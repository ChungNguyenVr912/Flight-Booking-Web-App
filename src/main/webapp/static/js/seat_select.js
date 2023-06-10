const displayCurrentPrice = document.getElementById('currentPrice');
const basePrice = document.getElementById('basePrice');
const luggagePrice = document.getElementById('luggagePrice');
const seatPriceDifference = document.getElementById('seatPriceDifference');

function changeSeatImg(element) {
    const bsSeats = document.querySelectorAll(".inputbusiness");
    bsSeats.forEach((item) => {
        const id = item.id;
        const label = document.getElementById("label" + id);
        if (item.checked) {
            label.innerHTML = '<img width=\"100px\" height=\"100px\" src=\"https://media.discordapp.net/attachments/646934670679998474/1116285812900904991/seat_selected-removebg-preview.png?width=625&height=625\" alt=\"seatSelected\">';
        } else {
            label.innerHTML = '<img width=\"100px\" height=\"100px\" src=\"https://media.discordapp.net/attachments/646934670679998474/1116256250217369630/seat_busi-removebg-preview.png?width=625&height=625\" alt=\"seatBusiness\">';
        }
    });

    const ecoSeats = document.querySelectorAll(".inputeconomy")
    ecoSeats.forEach((item) => {
        const id = item.id;
        const label = document.getElementById("label" + id);
        if (item.checked) {
            label.innerHTML = '<img width=\"100px\" height=\"100px\" src=\"https://media.discordapp.net/attachments/646934670679998474/1116285812900904991/seat_selected-removebg-preview.png?width=625&height=625\" alt=\"seatSelected\">';
        } else {
            label.innerHTML = '<img width=\"100px\" height=\"100px\" src=\"https://media.discordapp.net/attachments/646934670679998474/1116256249974108240/seat_eco-removebg-preview.png?width=625&height=625\" alt=\"seatEconomy\">';
        }
    });
    const displaySeatCode = document.getElementById('displaySeatCode');
    const displaySeatClass = document.getElementById('displaySeatClass');
    const seatPrice = document.getElementById(element.id.split("-")[0] + 'price')
    displaySeatCode.innerHTML = element.id.split("-")[0];
    const seatClass = element.id.split("-")[1];
    switch (seatClass) {
        case 'economy': {
            displaySeatClass.innerHTML = "Economy";
            break;
        }
        case 'business': {
            displaySeatClass.innerHTML = "Business";
        }
    }
    seatPriceDifference.value = parseInt(seatPrice.value) - parseInt(basePrice.value);
    displayCurrentPrice.value = 'VND ' + (parseInt(seatPriceDifference.value)
        + parseInt(basePrice.value) + parseInt(luggagePrice.value))
            .toLocaleString(undefined, {minimumFractionDigits: 0, maximumFractionDigits: 0, useGrouping:true});
}

function addLuggage(value) {
    const display = document.getElementById('displayLuggage');
    let price;
    switch (value) {
        case '2' : {
            display.innerHTML = '10kg';
            price = 150000;
            break;
        }
        case '3' : {
            display.innerHTML = '15kg';
            price = 200000;
            break;
        }
        case '4' : {
            display.innerHTML = '20kg';
            price = 250000;
            break;
        }
        case '5' : {
            display.innerHTML = '30kg';
            price = 350000;
            break;
        }
        default: {
            display.innerHTML = '';
            price = 0;
        }
    }
    luggagePrice.value = price;
    displayCurrentPrice.value = 'VND ' + (parseInt(basePrice.value)
        + parseInt(seatPriceDifference.value) + price)
        .toLocaleString(undefined, {minimumFractionDigits: 0, maximumFractionDigits: 0, useGrouping:true});
}