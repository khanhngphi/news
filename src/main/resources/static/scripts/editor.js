$(function () {
    $('#title-image-input').change(function () {
        var input = this;
        if (input.files[0]) {
            var formData = new FormData();
            formData.append("upload", this.files[0])

            $.ajax({
                url: '/api/upload',
                method: 'post',
                data: formData,
                enctype: 'multipart/form-data',
                contentType: false,
                processData: false,
                beforeSend: function () {
                    input.disabled = true;
                    $('.web-title-image-control-inner > label').text('Loading...');
                },
                complete: function () {
                    input.disabled = false;
                    $('.web-title-image-control-inner > label').text('Choose file');
                },
                success: function (data) {
                    $('.web-title-image').attr('src', data.url);
                    $('.web-title-image').attr('hidden', false);
                },
                error: function (xhr, textStatus, errorThrown) {
                    $('.web-title-image-control-inner > label').text(textStatus || errorThrown);
                }
            });
        }
    });

    $('#editorSave').click(function () {
        var button = this;

        var id = $('#articleId').val();

        var article = {
            title: CKEDITOR.instances.titleEditor.editable().getText(),
            subject: 'Mobile',
            time: new Date($('#articleDate').val() + ' ' + $('#articleTime').val()),
            image: $('#titleImage').attr('src'),
            brief: CKEDITOR.instances.briefEditor.editable().getText(),
            content: CKEDITOR.instances.contentEditor.getData(),
        }

        $.ajax({
            url: id ? '/api/article/' + id : '/api/article',
            method: id ? 'put' : 'post',
            data: JSON.stringify(article),
            contentType: 'application/json',
            // processData: false,
            beforeSend: function () {
                button.disabled = true;
                button.textContent = 'Saving...';
            },
            complete: function () {
                button.disabled = false;
                button.textContent = 'Save';
            },
            success: function (data, testStatus, request) {
                location.href = request.getResponseHeader('Location');
            },
            error: function (xhr, textStatus, errorThrown) {
                $('#serverError').text(textStatus || errorThrown);
            }
        });

    });

    $('#editorDelete').click(function () {
        var button = this;

        var id = $('#articleId').val();

        $.ajax({
            url: '/api/article/' + id,
            method: 'delete',
            beforeSend: function () {
                button.disabled = true;
                button.textContent = 'Deleting...';
            },
            complete: function () {
                button.disabled = false;
                button.textContent = 'Delete';
            },
            success: function (data) {
                location.href = '/';
            },
            error: function (xhr, textStatus, errorThrown) {
                $('#serverError').text(textStatus || errorThrown);
            }
        });

    })
});

$(window).on('unload', function (event) {
    var confirmationMessage = 'It looks like you have been editing something. '
        + 'If you leave before saving, your changes will be lost.';

    (event || window.event).returnValue = confirmationMessage; //Gecko + IE
    return confirmationMessage;
});