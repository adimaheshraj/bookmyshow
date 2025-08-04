document.addEventListener("DOMContentLoaded", () => {
  const movieContainer = document.getElementById("moviesContainer");

  if (movieContainer) {
    fetch("http://localhost:8080/api/movies")
      .then(res => res.json())
      .then(data => {
        data.forEach(movie => {
          const card = document.createElement("div");
          card.className = "movie-card";

          card.innerHTML = `
            <img src="${movie.posterUrl}" alt="${movie.title}" />
            <h3>${movie.title}</h3>
            <p>${movie.genre}</p>
            <button class="book-btn" data-title="${movie.title}">Book Now</button>
          `;

          movieContainer.appendChild(card);
        });

        // Attach event listener for all book buttons
        document.querySelectorAll(".book-btn").forEach(button => {
          button.addEventListener("click", () => {
            const movieTitle = button.getAttribute("data-title");
            const token = localStorage.getItem("token"); // JWT from login

            if (!token) {
              alert("Please login to book a movie.");
              return;
            }

            fetch("http://localhost:8080/api/bookings", {
              method: "POST",
              headers: {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`
              },
              body: JSON.stringify({ movieTitle })
            })
              .then(res => {
                if (!res.ok) {
                  return res.text().then(text => { throw new Error(text); });
                }
                return res.json();
              })
              .then(data => {
                alert(`Booking successful for: ${data.movie.title}`);
              })
              .catch(error => {
                console.error("Booking failed:", error.message);
                alert("Booking failed: " + error.message);
              });
          });
        });
      });
  }
});
