// data.js
const API_KEY = "579f4b021e6da2171513d42522d4a7f9"; // Replace with your API key
const BASE_URL = "https://api.themoviedb.org/3";

export const fetchMovies = async (category = "now_playing") => {
  try {
    const response = await fetch(
      `${BASE_URL}/movie/${category}?api_key=${API_KEY}&language=en-US&page=1`
    );
    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }
    const data = await response.json();
    return data.results;
  } catch (error) {
    console.error("Error fetching movies:", error);
    return [];
  }
};

export const fetchGenres = async () => {
  try {
    const response = await fetch(
      `${BASE_URL}/genre/movie/list?api_key=${API_KEY}&language=en-US`
    );
    if (!response.ok) {
      throw new Error(`HTTP error! Status: ${response.status}`);
    }
    const data = await response.json();
    return data.genres;
  } catch (error) {
    console.error("Error fetching genres:", error);
    return [];
  }
};
