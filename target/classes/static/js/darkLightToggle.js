var themeToggleDarkIcon = document.getElementById('theme-toggle-dark-icon');
var themeToggleLightIcon = document.getElementById('theme-toggle-light-icon');
var logo = document.getElementById('logo');

if (localStorage.getItem('color-theme') === 'dark' || (!('color-theme' in localStorage) && window.matchMedia('(prefers-color-scheme: dark)').matches)) {
    themeToggleLightIcon.classList.add('hidden');
    themeToggleDarkIcon.classList.remove('hidden');
    logo.src = 'img/dark-logo-svg.svg';
} else {
    themeToggleLightIcon.classList.remove('hidden');
    themeToggleDarkIcon.classList.add('hidden');
    logo.src = 'img/logo-svg.svg';
}

var themeToggleBtn = document.getElementById('theme-toggle');

themeToggleBtn.addEventListener('click', function () {
    themeToggleDarkIcon.classList.toggle('hidden');
    themeToggleLightIcon.classList.toggle('hidden');

    if (localStorage.getItem('color-theme')) {
        if (localStorage.getItem('color-theme') === 'light') {
            document.documentElement.classList.add('dark');
            localStorage.setItem('color-theme', 'dark');
            logo.src = 'img/dark-logo-svg.svg';
        } else {
            document.documentElement.classList.remove('dark');
            localStorage.setItem('color-theme', 'light');
            logo.src = 'img/logo-svg.svg';
        }
    } else {
        if (document.documentElement.classList.contains('dark')) {
            document.documentElement.classList.remove('dark');
            localStorage.setItem('color-theme', 'light');
            logo.src = 'img/logo-svg.svg';
        } else {
            document.documentElement.classList.add('dark');
            localStorage.setItem('color-theme', 'dark');
            logo.src = 'img/dark-logo-svg.svg';
        }
    }
});

var themeToggleDarkIconProfile = document.getElementById('theme-toggle-dark-icon-profile');
var themeToggleLightIconProfile = document.getElementById('theme-toggle-light-icon-profile');

if (localStorage.getItem('color-theme') === 'dark' || (!('color-theme' in localStorage) && window.matchMedia('(prefers-color-scheme: dark)').matches)) {
    themeToggleLightIconProfile.classList.add('hidden');
    themeToggleDarkIconProfile.classList.remove('hidden');
    logo.src = 'img/dark-logo-svg.svg';
} else {
    themeToggleLightIconProfile.classList.remove('hidden');
    themeToggleDarkIconProfile.classList.add('hidden');
    logo.src = 'img/logo-svg.svg';
}

var themeToggleBtnProfile = document.getElementById('theme-toggle-profile');

themeToggleBtnProfile.addEventListener('click', function () {
    themeToggleDarkIconProfile.classList.toggle('hidden');
    themeToggleLightIconProfile.classList.toggle('hidden');

    if (localStorage.getItem('color-theme')) {
        if (localStorage.getItem('color-theme') === 'light') {
            document.documentElement.classList.add('dark');
            localStorage.setItem('color-theme', 'dark');
            logo.src = 'img/dark-logo-svg.svg';
        } else {
            document.documentElement.classList.remove('dark');
            localStorage.setItem('color-theme', 'light');
            logo.src = 'img/logo-svg.svg';
        }
    } else {
        if (document.documentElement.classList.contains('dark')) {
            document.documentElement.classList.remove('dark');
            localStorage.setItem('color-theme', 'light');
            logo.src = 'img/logo-svg.svg';
        } else {
            document.documentElement.classList.add('dark');
            localStorage.setItem('color-theme', 'dark');
            logo.src = 'img/dark-logo-svg.svg';
        }
    }
});