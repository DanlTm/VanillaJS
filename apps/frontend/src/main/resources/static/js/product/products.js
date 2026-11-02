// Products page live search using pure Vanilla JavaScript
document.addEventListener("DOMContentLoaded", function () {
    const searchInput = document.getElementById("live-search-input");
    const btnReset = document.getElementById("btn-reset");
    const visibleCount = document.getElementById("visible-count");
    const noResults = document.getElementById("no-results");
    const cards = document.querySelectorAll(".product-card");
    cards.forEach(function (card) {
        card.addEventListener("click", function (event) {
            if (event.target.closest(".product-actions")) {
                return;
            }
            const wasSelected = card.classList.contains("product-card-selected");
            cards.forEach(function (otherCard) {
                otherCard.classList.remove("product-card-selected");
            });
            if (!wasSelected) {
                card.classList.add("product-card-selected");
            }
        });

        const deleteForm = card.querySelector(".action-delete").form;
        deleteForm.addEventListener("submit", function (event) {
            if (!window.confirm("Delete this product?")) {
                event.preventDefault();
            }
        });
    });

    function filter() {
        const query = searchInput.value.toLowerCase().trim();
        let count = 0;

        cards.forEach(function (card) {
            const name = (card.dataset.name || "").toLowerCase();
            const description = (card.dataset.description || "").toLowerCase();

            const matchText = !query || name.includes(query) || description.includes(query);

            if (matchText) {
                card.style.display = "";
                count++;
            } else {
                card.style.display = "none";
            }
        });

        visibleCount.textContent = count;
        noResults.style.display = count === 0 ? "block" : "none";
    }

    searchInput.addEventListener("input", filter);

    btnReset.addEventListener("click", function () {
        searchInput.value = "";
        filter();
        searchInput.focus();
    });

    visibleCount.textContent = cards.length;
});
