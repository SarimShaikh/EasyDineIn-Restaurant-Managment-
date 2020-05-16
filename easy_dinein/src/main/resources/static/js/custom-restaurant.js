$('.restaurantImage').change(function(){
    readImage(this);
});


function readImage(input) {
    if (input.files && input.files[0]) {
        var reader = new FileReader();

        reader.onload = function (e) {
            $('.restaurantImageDisplay').attr('src', e.target.result);
        }

        reader.readAsDataURL(input.files[0]);
    }
}