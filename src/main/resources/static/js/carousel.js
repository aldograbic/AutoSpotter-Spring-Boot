const carouselContainer = document.getElementById("indicators-carousel");

const carouselItems = Array.from(carouselContainer.getElementsByClassName("carousel-item"));
const prevButton = carouselContainer.querySelector("[data-carousel-prev]");
const nextButton = carouselContainer.querySelector("[data-carousel-next]");

let activeIndex = 0;

function showItem(index) {
    carouselItems.forEach((item, idx) => {
        item.classList.toggle("hidden", idx !== index);
    });
}

showItem(activeIndex);

prevButton.addEventListener("click", () => {
    activeIndex = (activeIndex - 1 + carouselItems.length) % carouselItems.length;
    showItem(activeIndex);
});

nextButton.addEventListener("click", () => {
    activeIndex = (activeIndex + 1) % carouselItems.length;
    showItem(activeIndex);
});