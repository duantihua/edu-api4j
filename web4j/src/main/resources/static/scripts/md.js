
var prefix = "";

var count = 0;

function addMD() {
	var newMD = jQuery("#" + prefix + "blueprint").clone(true);
	count++;
	newMD.attr('id', prefix + count);
	newMD.removeAttr('style');
	newMD.insertBefore(jQuery("#" + prefix + "addbutton"));
	
	newMD.find("select[role='department']").attr('name', prefix + count + ".depart.id").addClass("requiredDepart");
	newMD.find("select[role='span']").attr('name', prefix + count + ".level.id").addClass("requiredEducation");
	newMD.find(':text').each(function(index, input) {
		if(index == 0) {
			jQuery(input).attr('name', prefix + count + ".beginOn").addClass("requiredBeginOn");
		}
		if(index == 1) {
			jQuery(input).attr('name', prefix + count + ".endOn");
		}
	})
}

function removeMD(button) {
	jQuery(button).parent('div').remove();
}

function beforesubmit() {
	jQuery(":hidden[name=" + prefix + "count]").val(count);
}

