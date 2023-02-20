(function($) {
	$.fn.progressDialog = function() {
	};

	$.fn.progressDialog.showDialog = function(text) {
		text = text || "Loading,Please wait..."
		if (!processMask) {
			processMask = $("<div id='processMask'></div>").css({
				"height" : $(document).height() + "px",
				"width" : "100%",
				"filter" : "alpha(opacity = 40)",
				"-moz-opacity" : "0.4",
				"opacity" : "0.4",
				"background-color" : "#333",
				"position" : "absolute",
				"left" : "0px",
				"top" : "0px"
			});
		}
		if (!loadDiv) {
			loadDiv = $("<div id='loadDiv' style='width:"
					+ width
					+ "px; height:"
					+ Height
					+ "px;z-index:10;position:absolute;top:50%;left:"
					+ ($(window).width() - width / 2)
					/ 2
					+ "px'><div style='width:100%;font-weight:bolder; color:#FFFDFD;font-size:14px;line-height:22px;height:22px;'>"
					+ text
					+ "<img src='"
					+ App.contextPath
					+ "/static/scripts/images/indicator.gif' border='0' alt='loading' style='width:22px;height:22px;'/></div></div>");
		}
		$("body").prepend(loadDiv).append(processMask);
	}

	$.fn.progressDialog.hideDialog = function(text) {
		$("#processMask").remove();
		$("#loadDiv").remove();
	}

	var processMask;
	var loadDiv;
	var width = 290;
	var Height = 60;
})(jQuery);