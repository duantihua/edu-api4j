var prefix = "ru";

var count = 0;

function addRU(ruId, ruUsageId, ruCapacity,ruMaxCapacity) {
	var newRU = jQuery("#" + prefix + "_div_").clone(true);
	newRU.attr('id', prefix + "_div_" + count);
	newRU.removeAttr('style');
	newRU.insertBefore(jQuery("#" + prefix + "Addbutton"));
	
	newRU.find("select[role='usages']").attr('name', "roomUsage" + count + ".usage.id").addClass("usageCapacity").val(ruUsageId);
	newRU.find(":text").each(function(index, input) {
        if (0 == index) {
            jQuery(input).attr('name', "roomUsage" + count + ".capacity").addClass("capacityNum").val(ruCapacity);
        }else{
        	jQuery(input).attr('name', "roomUsage" + count + ".maxCapacity").addClass("capacityNum").val(ruMaxCapacity);
        }
    });
	newRU.find(":hidden").each(function(index, input) {
	    if (0 == index) {
	        jQuery(input).attr('name', "roomUsage" + count + ".id").val(ruId);
	    }
	});
	count++;
}

function removeRU(button) {
    jQuery(button).parent('div').remove();
}

