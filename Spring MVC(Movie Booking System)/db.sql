-- Create the database
CREATE DATABASE IF NOT EXISTS movie_ticket_booking;
USE movie_ticket_booking;

-- Users Table
CREATE TABLE IF NOT EXISTS users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    role ENUM('NORMAL_USER', 'THEATRE_ADMIN', 'APPLICATION_ADMIN') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Theatres Table
CREATE TABLE IF NOT EXISTS theatres (
    theatre_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    address VARCHAR(255) NOT NULL,
    city VARCHAR(50) NOT NULL,
    admin_id INT NOT NULL,
    FOREIGN KEY (admin_id) REFERENCES users(user_id)
);

-- Screens Table
CREATE TABLE IF NOT EXISTS screens (
    screen_id INT AUTO_INCREMENT PRIMARY KEY,
    theatre_id INT NOT NULL,
    name VARCHAR(50) NOT NULL,
    total_seats INT NOT NULL,
    FOREIGN KEY (theatre_id) REFERENCES theatres(theatre_id)
);

-- Seats Table
CREATE TABLE IF NOT EXISTS seats (
    seat_id INT AUTO_INCREMENT PRIMARY KEY,
    screen_id INT NOT NULL,
    seat_number VARCHAR(10) NOT NULL,
    is_booked BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (screen_id) REFERENCES screens(screen_id)
);

-- Movies Table
CREATE TABLE IF NOT EXISTS movies (
    movie_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    description TEXT,
    duration_minutes INT NOT NULL,
    release_date DATE NOT NULL,
    image_url VARCHAR(255),
    language VARCHAR(50) NOT NULL
);

-- Shows Table
CREATE TABLE IF NOT EXISTS shows (
    show_id INT AUTO_INCREMENT PRIMARY KEY,
    movie_id INT NOT NULL,
    screen_id INT NOT NULL,
    start_time DATETIME NOT NULL,
    end_time DATETIME NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id),
    FOREIGN KEY (screen_id) REFERENCES screens(screen_id)
);

-- Bookings Table
CREATE TABLE IF NOT EXISTS bookings (
    booking_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    show_id INT NOT NULL,
    booking_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    total_amount DECIMAL(10, 2) NOT NULL,
    qr_code_path VARCHAR(255),
    pdf_path VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (show_id) REFERENCES shows(show_id)
);

-- Booking_Seats Table (Mapping)
CREATE TABLE IF NOT EXISTS booking_seats (
    booking_id INT NOT NULL,
    seat_id INT NOT NULL,
    PRIMARY KEY (booking_id, seat_id),
    FOREIGN KEY (booking_id) REFERENCES bookings(booking_id),
    FOREIGN KEY (seat_id) REFERENCES seats(seat_id)
);

-- Bank_Details Table
CREATE TABLE IF NOT EXISTS bank_details (
    bank_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    card_number VARCHAR(16) NOT NULL,
    card_holder_name VARCHAR(100) NOT NULL,
    expiry_date VARCHAR(7) NOT NULL,
    cvv VARCHAR(3) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(user_id)
);

-- Cities Table
CREATE TABLE IF NOT EXISTS cities (
    city_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

-- Reviews Table
CREATE TABLE IF NOT EXISTS reviews (
    review_id INT AUTO_INCREMENT PRIMARY KEY,
    user_id INT NOT NULL,
    movie_id INT NOT NULL,
    rating INT NOT NULL CHECK (rating BETWEEN 1 AND 5),
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id)
);

-- Promotions Table
CREATE TABLE IF NOT EXISTS promotions (
    promotion_id INT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(20) NOT NULL UNIQUE,
    discount_percentage DECIMAL(5, 2) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL
);

-- Payments Table
CREATE TABLE IF NOT EXISTS payments (
    payment_id INT AUTO_INCREMENT PRIMARY KEY,
    booking_id INT NOT NULL,
    amount DECIMAL(10, 2) NOT NULL,
    payment_method VARCHAR(50) NOT NULL,
    payment_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    transaction_id VARCHAR(100) NOT NULL,
    FOREIGN KEY (booking_id) REFERENCES bookings(booking_id)
);

-- Insert Sample Data

-- Users
INSERT INTO users (username, email, password_hash, role) VALUES
('admin', 'admin@example.com', '$2a$12$N9qo8uLOickgx2ZMRZoMy.MrUQYXQYXQYXQYXQYXQYXQYXQYXQYXQY', 'APPLICATION_ADMIN'),
('theatre_admin', 'theatre@example.com', '$2a$12$N9qo8uLOickgx2ZMRZoMy.MrUQYXQYXQYXQYXQYXQYXQYXQYXQYXQY', 'THEATRE_ADMIN'),
('user1', 'user1@example.com', '$2a$12$N9qo8uLOickgx2ZMRZoMy.MrUQYXQYXQYXQYXQYXQYXQYXQYXQYXQY', 'NORMAL_USER'),
('user2', 'user2@example.com', '$2a$12$N9qo8uLOickgx2ZMRZoMy.MrUQYXQYXQYXQYXQYXQYXQYXQYXQYXQY', 'NORMAL_USER');

-- Cities
INSERT INTO cities (name) VALUES
('New York'), ('Los Angeles'), ('Chicago'), ('Houston'), ('Phoenix');

-- Theatres
INSERT INTO theatres (name, address, city, admin_id) VALUES
('Cineplex Downtown', '123 Main St', 'New York', 2),
('Silver Screen', '456 Broadway', 'Los Angeles', 2),
('Movie World', '789 Oak Ave', 'Chicago', 2);

-- Screens
INSERT INTO screens (theatre_id, name, total_seats) VALUES
(1, 'Screen 1', 100),
(1, 'Screen 2', 80),
(2, 'Screen 1', 120),
(2, 'Screen 2', 90),
(3, 'Screen 1', 110);

-- Seats (Sample for Screen 1, Theatre 1)
INSERT INTO seats (screen_id, seat_number) VALUES
(1, 'A1'), (1, 'A2'), (1, 'A3'), (1, 'A4'), (1, 'A5'),
(1, 'B1'), (1, 'B2'), (1, 'B3'), (1, 'B4'), (1, 'B5');

-- Movies
INSERT INTO movies (title, description, duration_minutes, release_date, image_url, language) VALUES
('The Dark Knight', 'A superhero movie about Batman.', 152, '2008-07-18', 'https://play-lh.googleusercontent.com/5ApgwKo_GwEtc5khfQFFe2iJnoIPtsMF1dEhn49rQn27pJK7DVgT4v9MyKMEO4xfKxKR=w240-h480-rw', 'English'),
('Inception', 'A sci-fi thriller about dreams.', 148, '2010-07-16', 'https://upload.wikimedia.org/wikipedia/en/2/2e/Inception_%282010%29_theatrical_poster.jpg', 'English'),
('The Shawshank Redemption', 'A drama about hope and friendship.', 142, '1994-09-23', 'https://upload.wikimedia.org/wikipedia/en/2/2e/Inception_%282010%29_theatrical_poster.jpg', 'English'),
('Pulp Fiction', 'A crime movie with intertwining stories.', 154, '1994-10-14', 'https://www.tallengestore.com/cdn/shop/products/PulpFiction-JohnTravoltaAndSamuelLJackson-MovieStill1_92bdb33d-350e-43b9-a06e-abc499510a30.jpg?v=1684129892', 'English'),
('The Godfather', 'A crime saga about a mafia family.', 175, '1972-03-24', 'https://m.media-amazon.com/images/M/MV5BNGEwYjgwOGQtYjg5ZS00Njc1LTk2ZGEtM2QwZWQ2NjdhZTE5XkEyXkFqcGc@._V1_FMjpg_UX1000_.jpg', 'English');

-- Shows
INSERT INTO shows (movie_id, screen_id, start_time, end_time, price) VALUES
(1, 1, '2025-11-01 18:00:00', '2025-11-01 20:32:00', 12.99),
(2, 1, '2025-11-02 19:00:00', '2025-11-02 21:28:00', 11.99),
(3, 2, '2025-11-03 17:00:00', '2025-11-03 19:22:00', 10.99),
(4, 2, '2025-11-04 20:00:00', '2025-11-04 22:34:00', 13.99),
(5, 3, '2025-11-05 18:30:00', '2025-11-05 21:25:00', 14.99);

-- Bookings
INSERT INTO bookings (user_id, show_id, total_amount) VALUES
(3, 1, 25.98),
(3, 2, 23.98),
(4, 3, 21.98);

-- Booking_Seats
INSERT INTO booking_seats (booking_id, seat_id) VALUES
(1, 1), (1, 2),
(2, 3), (2, 4),
(3, 5), (3, 6);

-- Bank_Details
INSERT INTO bank_details (user_id, card_number, card_holder_name, expiry_date, cvv) VALUES
(3, '4111111111111111', 'John Doe', '12/28', '123'),
(4, '5555555555554444', 'Jane Smith', '06/27', '456');

-- Reviews
INSERT INTO reviews (user_id, movie_id, rating, comment) VALUES
(3, 1, 5, 'One of the best movies ever!'),
(3, 2, 4, 'Mind-blowing concept.'),
(4, 3, 5, 'A timeless classic.'),
(4, 4, 4, 'Quentin Tarantino at his best.');

-- Promotions
INSERT INTO promotions (code, discount_percentage, start_date, end_date) VALUES
('WELCOME20', 20.00, '2025-10-01', '2025-12-31'),
('SUMMER15', 15.00, '2025-06-01', '2025-08-31');

-- Payments
INSERT INTO payments (booking_id, amount, payment_method, transaction_id) VALUES
(1, 25.98, 'Credit Card', 'TXN123456789'),
(2, 23.98, 'Credit Card', 'TXN987654321'),
(3, 21.98, 'Debit Card', 'TXN456123789');


-- UPDATE users SET role = 'THEATRE_ADMIN' WHERE user_id = 3;

