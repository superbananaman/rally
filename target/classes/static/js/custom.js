var swiper = [];
$(document).ready(function () {
	$(document).on('submit', '#uploadForm', function () {
		var file = this.file.files[0];
		var itemName = this.childNodes[1].innerText
		var formData = new FormData();
		formData.append('file', file);
		$.ajax({
		    url: 'http://'+ window.location.host + '/store',
            type: "post",
            contentType: false,
            cache: false,
            enctype: "multipart/form-data",
			async: false,
			data: formData,
			processData:false,
			headers:{"item" : itemName}
		});
		window.location.href = 'http://'+ window.location.host + '/upload';
		return false;
	});
	
$(".swiper-slide").each(function(index, element){
	if(element.children.length == 0){
		console.log("rmvd");
		element.parentElement.removeChild(element);
	}
	
});

	
$(".swiper-container").each(function(index, element){
    var $this = $(this);
    $this.addClass("instance-" + index);
    $this.find(".swiper-button-prev").addClass("btn-prev-" + index);
    $this.find(".swiper-button-next").addClass("btn-next-" + index);
    var swiper = new Swiper(".instance-" + index, {
    	loop: true,
    	zoom: true,
    	zoomToggle: true,
    	spaceBetween: 5,
        nextButton: ".btn-next-" + index,
        prevButton: ".btn-prev-" + index
    });
});
	
});