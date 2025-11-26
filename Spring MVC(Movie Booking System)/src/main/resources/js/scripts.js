document.addEventListener('DOMContentLoaded', function() {
    // Seat selection logic
    const seats = document.querySelectorAll('.seat.available');
    seats.forEach(seat => {
        seat.addEventListener('click', function() {
            const checkbox = this.querySelector('input[type="checkbox"]');
            if (checkbox) {
                checkbox.checked = !checkbox.checked;
                this.style.background = checkbox.checked ? '#45a049' : '#4CAF50';
            }
        });
    });
});
