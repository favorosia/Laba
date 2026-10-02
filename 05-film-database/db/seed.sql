BEGIN;

INSERT INTO movies (title, release_year) VALUES
('The Godfather', 1972), ('The Shawshank Redemption', 1994), ('Casablanca', 1942),
('12 Angry Men', 1957), ('The Dark Knight', 2008), ('Schindler''s List', 1993),
('Pulp Fiction', 1994), ('Forrest Gump', 1994), ('The Lord of the Rings: The Fellowship of the Ring', 2001),
('The Lord of the Rings: The Two Towers', 2002), ('The Lord of the Rings: The Return of the King', 2003),
('Goodfellas', 1990), ('One Flew Over the Cuckoo''s Nest', 1975), ('Seven Samurai', 1954),
('City of God', 2002), ('Spirited Away', 2001), ('Parasite', 2019), ('Whiplash', 2014),
('The Grand Budapest Hotel', 2014), ('The Matrix', 1999), ('Interstellar', 2014),
('Inception', 2010), ('The Silence of the Lambs', 1991), ('Saving Private Ryan', 1998),
('Gladiator', 2000), ('Amélie', 2001), ('The Pianist', 2002), ('Alien', 1979),
('The Truman Show', 1998), ('Back to the Future', 1985), ('Jaws', 1975),
('The Lion King', 1994), ('Coco', 2017), ('The Social Network', 2010),
('Life Is Beautiful', 1997), ('The Intouchables', 2011), ('La La Land', 2016),
('Mad Max: Fury Road', 2015), ('The Departed', 2006), ('Dune: Part Two', 2024)
ON CONFLICT (title, release_year) DO NOTHING;

INSERT INTO actors (full_name, birth_year) VALUES
('Marlon Brando', 1924), ('Al Pacino', 1940), ('James Caan', 1940), ('Tim Robbins', 1958),
('Morgan Freeman', 1937), ('Humphrey Bogart', 1899), ('Ingrid Bergman', 1915),
('Henry Fonda', 1905), ('Lee J. Cobb', 1911), ('Christian Bale', 1974),
('Heath Ledger', 1979), ('Liam Neeson', 1952), ('Ralph Fiennes', 1962),
('John Travolta', 1954), ('Samuel L. Jackson', 1948), ('Tom Hanks', 1956),
('Robin Wright', 1966), ('Elijah Wood', 1981), ('Ian McKellen', 1939),
('Viggo Mortensen', 1958), ('Robert De Niro', 1943), ('Jack Nicholson', 1937),
('Toshiro Mifune', 1920), ('Takashi Shimura', 1905), ('Song Kang-ho', 1967),
('Choi Woo-shik', 1990), ('Miles Teller', 1987), ('J. K. Simmons', 1955),
('Ralph Fiennes', 1962), ('Keanu Reeves', 1964), ('Laurence Fishburne', 1961),
('Leonardo DiCaprio', 1974), ('Joseph Gordon-Levitt', 1981), ('Anthony Hopkins', 1937),
('Jodie Foster', 1962), ('Matt Damon', 1970), ('Russell Crowe', 1964),
('Audrey Tautou', 1976), ('Adrien Brody', 1973), ('Sigourney Weaver', 1949),
('Jim Carrey', 1962), ('Michael J. Fox', 1961), ('Roy Scheider', 1932),
('Matthew Broderick', 1962), ('Demi Moore', 1962), ('Ben Affleck', 1972),
('Roberto Benigni', 1952), ('Omar Sy', 1978), ('Emma Stone', 1988),
('Ryan Gosling', 1980), ('Charlize Theron', 1975), ('Cillian Murphy', 1976),
('Timothée Chalamet', 1995), ('Zendaya', 1996), ('Florence Pugh', 1996)
ON CONFLICT (full_name, birth_year) DO NOTHING;

INSERT INTO film_roles (actor_id, movie_id, role_name)
SELECT a.id, m.id, v.role_name
FROM (VALUES
    ('Marlon Brando', 1924, 'The Godfather', 1972, 'Vito Corleone'),
    ('Al Pacino', 1940, 'The Godfather', 1972, 'Michael Corleone'),
    ('James Caan', 1940, 'The Godfather', 1972, 'Sonny Corleone'),
    ('Tim Robbins', 1958, 'The Shawshank Redemption', 1994, 'Andy Dufresne'),
    ('Morgan Freeman', 1937, 'The Shawshank Redemption', 1994, 'Ellis Boyd Redding'),
    ('Humphrey Bogart', 1899, 'Casablanca', 1942, 'Rick Blaine'),
    ('Ingrid Bergman', 1915, 'Casablanca', 1942, 'Ilsa Lund'),
    ('Henry Fonda', 1905, '12 Angry Men', 1957, 'Juror 8'),
    ('Lee J. Cobb', 1911, '12 Angry Men', 1957, 'Juror 3'),
    ('Christian Bale', 1974, 'The Dark Knight', 2008, 'Bruce Wayne'),
    ('Heath Ledger', 1979, 'The Dark Knight', 2008, 'Joker'),
    ('Liam Neeson', 1952, 'Schindler''s List', 1993, 'Oskar Schindler'),
    ('Ralph Fiennes', 1962, 'Schindler''s List', 1993, 'Amon Göth'),
    ('John Travolta', 1954, 'Pulp Fiction', 1994, 'Vincent Vega'),
    ('Samuel L. Jackson', 1948, 'Pulp Fiction', 1994, 'Jules Winnfield'),
    ('Tom Hanks', 1956, 'Forrest Gump', 1994, 'Forrest Gump'),
    ('Robin Wright', 1966, 'Forrest Gump', 1994, 'Jenny Curran'),
    ('Elijah Wood', 1981, 'The Lord of the Rings: The Fellowship of the Ring', 2001, 'Frodo Baggins'),
    ('Ian McKellen', 1939, 'The Lord of the Rings: The Fellowship of the Ring', 2001, 'Gandalf'),
    ('Viggo Mortensen', 1958, 'The Lord of the Rings: The Return of the King', 2003, 'Aragorn'),
    ('Robert De Niro', 1943, 'Goodfellas', 1990, 'James Conway'),
    ('Jack Nicholson', 1937, 'One Flew Over the Cuckoo''s Nest', 1975, 'Randle McMurphy'),
    ('Toshiro Mifune', 1920, 'Seven Samurai', 1954, 'Kikuchiyo'),
    ('Takashi Shimura', 1905, 'Seven Samurai', 1954, 'Kambei Shimada'),
    ('Song Kang-ho', 1967, 'Parasite', 2019, 'Kim Ki-taek'),
    ('Choi Woo-shik', 1990, 'Parasite', 2019, 'Kim Ki-woo'),
    ('Miles Teller', 1987, 'Whiplash', 2014, 'Andrew Neiman'),
    ('J. K. Simmons', 1955, 'Whiplash', 2014, 'Terence Fletcher'),
    ('Keanu Reeves', 1964, 'The Matrix', 1999, 'Neo'),
    ('Laurence Fishburne', 1961, 'The Matrix', 1999, 'Morpheus'),
    ('Leonardo DiCaprio', 1974, 'Inception', 2010, 'Dom Cobb'),
    ('Joseph Gordon-Levitt', 1981, 'Inception', 2010, 'Arthur'),
    ('Anthony Hopkins', 1937, 'The Silence of the Lambs', 1991, 'Hannibal Lecter'),
    ('Jodie Foster', 1962, 'The Silence of the Lambs', 1991, 'Clarice Starling'),
    ('Matt Damon', 1970, 'Saving Private Ryan', 1998, 'James Ryan'),
    ('Russell Crowe', 1964, 'Gladiator', 2000, 'Maximus'),
    ('Audrey Tautou', 1976, 'Amélie', 2001, 'Amélie Poulain'),
    ('Adrien Brody', 1973, 'The Pianist', 2002, 'Władysław Szpilman'),
    ('Sigourney Weaver', 1949, 'Alien', 1979, 'Ellen Ripley'),
    ('Jim Carrey', 1962, 'The Truman Show', 1998, 'Truman Burbank'),
    ('Michael J. Fox', 1961, 'Back to the Future', 1985, 'Marty McFly'),
    ('Roy Scheider', 1932, 'Jaws', 1975, 'Martin Brody'),
    ('Matthew Broderick', 1962, 'The Lion King', 1994, 'Simba'),
    ('Ben Affleck', 1972, 'The Social Network', 2010, 'Cameron Winklevoss'),
    ('Roberto Benigni', 1952, 'Life Is Beautiful', 1997, 'Guido Orefice'),
    ('Omar Sy', 1978, 'The Intouchables', 2011, 'Driss'),
    ('Emma Stone', 1988, 'La La Land', 2016, 'Mia Dolan'),
    ('Ryan Gosling', 1980, 'La La Land', 2016, 'Sebastian Wilder'),
    ('Charlize Theron', 1975, 'Mad Max: Fury Road', 2015, 'Imperator Furiosa'),
    ('Leonardo DiCaprio', 1974, 'The Departed', 2006, 'Billy Costigan'),
    ('Cillian Murphy', 1976, 'The Dark Knight', 2008, 'Dr. Jonathan Crane'),
    ('Timothée Chalamet', 1995, 'Dune: Part Two', 2024, 'Paul Atreides'),
    ('Zendaya', 1996, 'Dune: Part Two', 2024, 'Chani'),
    ('Florence Pugh', 1996, 'Dune: Part Two', 2024, 'Princess Irulan')
) AS v(actor_name, birth_year, movie_title, release_year, role_name)
JOIN actors a ON a.full_name = v.actor_name AND a.birth_year = v.birth_year
JOIN movies m ON m.title = v.movie_title AND m.release_year = v.release_year
ON CONFLICT (actor_id, movie_id, role_name) DO NOTHING;

COMMIT;
