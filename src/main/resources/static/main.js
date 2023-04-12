function goBack() {
    window.history.back();
}

function sortByPriceAsc() {
    const wishlist = document.querySelectorAll("tr");
    Array.from(wishlist).slice(1).sort((a, b) => {
        const priceA = parseFloat(a.children[2].textContent);
        const priceB = parseFloat(b.children[2].textContent);
        return priceA - priceB;
    }).forEach(tr => wishlist[0].parentNode.appendChild(tr));
}

function sortByPriceDesc() {
    const wishlist = document.querySelectorAll("tr");
    Array.from(wishlist).slice(1).sort((a, b) => {
        const priceA = parseFloat(a.children[2].textContent);
        const priceB = parseFloat(b.children[2].textContent);
        return priceB - priceA;
    }).forEach(tr => wishlist[0].parentNode.appendChild(tr));
}


