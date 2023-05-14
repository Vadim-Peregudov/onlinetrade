$(document).ready(function () {
    $('.col img').click(function () {
        let thisImage = $(this);
        let thisImageSrc = thisImage.attr('src');

        // Убираем рамку с предыдущей выбранной фотографии
        $('.col img').removeClass('active');
        // Добавляем рамку на выбранную фотографию
        thisImage.addClass('active');

        $('.big-image').attr('src', thisImageSrc);
    });


});
