function navigateTo(url) {
    window.location.href = url;
}

document.addEventListener("DOMContentLoaded", function () {
    const elements = document.querySelectorAll(".fade-in");

    const options = {
        root: null,
        rootMargin: "0px",
        threshold: 0.3
    };

    const observer = new IntersectionObserver(function (entries, observer) {
        entries.forEach(entry => {
            if (entry.isIntersecting) {
                entry.target.classList.add("fade-in-visible");
                observer.unobserve(entry.target);
            }
        });
    }, options);

    elements.forEach(element => {
        observer.observe(element);
    });

    const buttons = document.querySelectorAll("button[onclick]");
                    buttons.forEach(button => {
                        button.addEventListener("click", function() {
                            const url = button.getAttribute("onclick").split("(")[1].split(")")[0].replace(/'/g, "");
                            navigateTo(url);
                        });
                    });
});