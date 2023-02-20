(function() {
	(function($) {
		return $.fn.ajaxChosen = function(settings, callback, chosenOptions) {
			var defaultOptions, options, select;
			if (settings == null) {
				settings = {};
			}
			defaultOptions = {
				minTermLength : 1,
				jsonTermKey : "term"
			};
			select = this;
			options = $.extend({}, defaultOptions, $(select).data(), settings);
			if (!chosenOptions) {
				chosenOptions = {};
			}
			if (!chosenOptions.placeholder_text) {
				chosenOptions.placeholder_text = "请输入内容查询";
			}
			if (!chosenOptions.no_results_text) {
				chosenOptions.no_results_text = "没有匹配结果";
			}
			this.chosen(chosenOptions);
			var __input;
			if (this.prop('multiple')) {
				__input = this.next('.chosen-container').find(
						".search-field > input");
			} else {
				__input = this.next('.chosen-container').find(
						".chosen-search > input");
			}

			__input.bind('qj:execute', function() {
				var field, msg, success, untrimmed_val, val;
				untrimmed_val = $(this).val();
				val = $.trim($(this).val());
				if (val === $(this).data('prevVal') && untrimmed_val.length>0) {
					return false;
				}
				$(this).data('prevVal', untrimmed_val);
				if (val.length < options.minTermLength) {
					return false;
				}
				field = $(this);
				if (options.data == null) {
					options.data = {};
				}
				options.data[options.jsonTermKey] = val;
				if (options.postData != undefined) {
					var extraData = options.postData();
					$.each(extraData, function(key, value) {
						options.data[key] = value;
					});
				}
				success = options.success;
				options.success = function(data) {
					var items, nbItems, selected_values;
					if (data == null) {
						return;
					}
					selected_values = [];
					select.find('option').each(
							function() {
								if (!$(this).is(":selected")) {
									return $(this).remove();
								} else {
									return selected_values.push($(this).val()
											+ "-" + $(this).text());
								}
							});
					items = callback(data);
					nbItems = 0;
					$.each(items,
							function(value, text) {
								nbItems++;
								if ($.inArray(value + "-" + text,
										selected_values) === -1) {
									return $("<option />").val(value)
											.html(text).appendTo(select);
								}
							});
					if (nbItems) {
						select.trigger("chosen:updated.chosen");
					} else {
						select.data().chosen.no_results_clear();
						select.data().chosen.no_results(field.val());
					}
					if (settings.success != null) {
						settings.success(data);
					}
					return field.val(untrimmed_val);
				};
				return $.ajax(options);
			});

			var lastKeyUpTime = null;
			// 界定是否在输入的阈值（单位:毫秒）,如果一个用户在n毫秒内没有输入动作，那么就可以认为用户已经输入完毕可以执行ajax动作了
			var typingThreshold = 800;
			__input.bind("keyup", function() {
				lastKeyUpTime = new Date().getTime();
				setTimeout(function() {
					var currentKeyUpTime = new Date().getTime();
					if (currentKeyUpTime - lastKeyUpTime > typingThreshold) {
						__input.trigger('qj:execute');
					}
				}, typingThreshold + 100);
			});
			return __input;
		};
	})(jQuery);
}).call(this);
