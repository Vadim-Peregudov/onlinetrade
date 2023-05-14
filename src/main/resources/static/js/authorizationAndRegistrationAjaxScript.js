$(document).ready(function () {

    if (localStorage.getItem('roles') != null) {
        const roles = JSON.parse(localStorage.getItem('roles'));
        updateNavMenu(roles);
    }


    $("#form-authorization").submit(function (e) {
        e.preventDefault(); // Остановить отправку формы по умолчанию

        // Получить значения полей email и password
        let email = $("#emailInput").val();
        let password = $("#passwordInput").val();

        let authenticationRequest = {
            login: email,
            password: password
        }
        console.log(authenticationRequest);

        // Отправить запрос на сервер для проверки авторизации
        $.ajax({
            type: "POST",
            url: "/api/auth/authenticate",
            data: JSON.stringify(authenticationRequest),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                localStorage.setItem("roles", JSON.stringify(data.roles));

                setCookie('jwtToken', data.token, 30);

                updateNavMenu(data.roles);

                $('#loginModal').modal('hide');

            },
            error: function (jqXHR, textStatus, errorThrown) {
                console.log(textStatus, errorThrown); // выводим ошибку в консоль
            }
        });
    });

    $("#form-registration").submit(function (e) {
        e.preventDefault(); // Остановить отправку формы по умолчанию

        // Получить значения полей email и password
        let username = $("#username").val();
        let lastName = $("#lastName").val()
        let email = $("#email").val();
        let phone = $("#phone").val();
        let password = $("#password").val();

        let registerRequest = {
            firstName: username,
            lastName: lastName,
            login: email,
            password: password,
            phone: phone,
        }
        console.log(registerRequest);

        $.ajax({
            type: "POST",
            url: "/api/auth/registration",
            data: JSON.stringify(registerRequest),
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                $('#registerModal').modal('hide');
                $('#loginModal').modal('show');
            },
            error: function () {
                displayErrorMessageRegistration("Ошибка сервера");
            }
        })
    })

    $('.dropdown-menu .dropdown-item').on('click', function () {
        let categoryName = $(this).parents('.dropdown').find('.nav-link').text().trim()
        let productName = $(this).val();

        window.location.href = `/catalog?category=${categoryName}&model=${productName}`;
    });

    $('.navbar').on('updateCountProduct', function () {
        updateCount();
    });

    if (JSON.parse(localStorage.getItem('basket')) != null) {
        updateCount();
    }


});

function displayErrorMessageAuthorization(message) {
    $("#errorMessageAuthorization").text(message);
}

function displayErrorMessageRegistration(message) {
    $("#errorMessageRegistration").text(message);
}

function createNavItem(text, href) {
    let navItem = $(`<li>`, {class: 'nav-item'});
    let a = $('<a>', {
        class: `nav-link`
        , text: text
        , href: href
    });
    if (text === 'КАРЗИНА') {
        let count = $('<span>', {
            class: 'badge bg-danger count text-white',
        });
        a.append(count)
    }

    navItem.append(a);

    return navItem;
}

function createNavItemButtonExit() {
    let navItem = $(`<li>`, {class: 'nav-item'});
    let button = $(`<button>`, {
        class: `button-exit btn nav-link button-navbar`,
        type: `button`,
        text: `Выход`,
        click: function () {
            $.ajax({
                url: '/api/logout',
                type: 'POST',
                success: function (result) {
                    localStorage.removeItem('roles');
                    // Перенаправление на страницу логина
                    window.location.href = '/home';
                },
                error: function (xhr, status, error) {
                    console.log('Ошибка выполнения запроса: ' + error);
                }
            });
        }
    });

    navItem.append(button);

    return navItem;
}

function setCookie(name, value, minutes) {
    let expires = "";
    if (minutes) {
        let date = new Date();
        date.setTime(date.getTime() + (minutes * 60 * 1000));
        expires = "; expires=" + date.toUTCString();
    }
    document.cookie = name + "=" + (value || "") + expires + "; path=/; secure; SameSite=Strict";
    //document.cookie = name + "=" + (value || "") + expires + "; path=/; secure; SameSite=Strict; httpOnly";
    /*
        Для того, чтобы настроить HTTPS в проекте Spring можно воспользоваться самоподписанным SSL-сертификатом.
        Следуя инструкциям, Вы сможете создать такой сертификат и настроить его в своем проекте:

       Сгенерируйте самоподписанный SSL-сертификат:

       yaml
       Copy code
       keytool -genkeypair -alias myapp -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore keystore.p12 -validity 3650
       Во время выполнения этой команды Вам потребуется ввести некоторые параметры, такие как пароль, имя организации и др.

       Скопируйте созданный файл keystore.p12 в папку src/main/resources Вашего проекта.

       Отредактируйте файл application.properties и добавьте следующие настройки:

       server.port=8443
       server.ssl.key-store-type=PKCS12
       server.ssl.key-store=classpath:keystore.p12
       server.ssl.key-store-password=<password>
       server.ssl.key-alias=myapp
       Замените <password> на пароль, который Вы вводили на шаге 1.
     */
}

function updateNavMenu(roleList) {
    console.log(roleList);
    if (roleList === null) {
        return;
    }
    roleList.forEach(role => {
        if (role.name === 'ROLE_USER') {
            let navBar = $(`.menu`);
            navBar.empty();
            navBar.append(createNavItem(`КАРЗИНА`, `/basket/page`))
                .append(createNavItem(`ЗАКАЗЫ`, `/user/orders`))
                .append(createNavItemButtonExit());
        }

        if (role.name === 'ROLE_ADMIN') {
            let navBar = $(`.menu`);
            navBar.empty();
            navBar.append(createNavItem('ПАНЕЛЬ АДМИНИСТРАЦИИ', '/admin/main'))
                .append(createNavItemButtonExit());
        }
    });

}

function updateCount() {
    let basket = JSON.parse(localStorage.getItem('basket'));

    let totalProductsCount = 0;
    for (let key in basket) {
        if (basket.hasOwnProperty(key)) {
            totalProductsCount += parseInt(basket[key]);
        }
    }

    $('.navbar .count').text(totalProductsCount);
}