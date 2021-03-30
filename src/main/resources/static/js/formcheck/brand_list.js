$(function () {
    toastr.options = {
        closeButton: false,
        debug: false,
        progressBar: true,
        positionClass: "toast-bottom-center",
        onclick: null,
        showDuration: "300",
        hideDuration: "3000",
        timeOut: "3000",
        extendedTimeOut: "3000",
        showEasing: "swing",
        hideEasing: "linear",
        showMethod: "fadeIn",
        hideMethod: "fadeOut"
    };
    $('.btn.btn-info').click(function () {
        toastr.success('新增成功');
    });
});