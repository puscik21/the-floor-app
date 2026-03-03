DELETE
FROM player;

INSERT INTO player (name, category, is_playing, win_streak, duels_won, time_boosts_available, time_boosts_used)
VALUES ('Madzia', 'Wędkarstwo', true, 0, 0, 3, 0),
       ('Igor', 'Historia', true, 1, 1, 2, 1),
       ('Nati', 'Muzyka', true, 0, 0, 3, 0),
       ('Grześ', 'Informatyka', true, 2, 3, 1, 2),
       ('Seba', 'Sport', true, 0, 0, 3, 0),
       ('The Michał', 'Matematyka', true, 1, 2, 2, 1),
       ('Tomek', 'Geografia', true, 0, 0, 3, 0),
       ('Monika', 'Literatura', true, 0, 1, 3, 0),
       ('Madzia 2', 'Biologia', true, 0, 0, 3, 0);

-- -- Additional players
-- INSERT INTO player (name, category, is_playing, win_streak, duels_won, time_boosts_available, time_boosts_used)
-- VALUES ('Kuba', 'Filmy Marvela', true, 0, 0, 3, 0),
--        ('Ania', 'Kuchnie Świata', true, 0, 2, 2, 1),
--        ('Bartek', 'Motoryzacja', true, 3, 5, 0, 3),
--        ('Zuzia', 'Astronomia', true, 0, 0, 3, 0),
--        ('Rafał', 'Gry Wideo', true, 0, 0, 3, 0),
--        ('Kasia', 'Moda', true, 1, 1, 2, 1),
--        ('Piotrek', 'Polityka', true, 0, 0, 3, 0),
--        ('Ola', 'Sztuka', true, 0, 0, 3, 0),
--        ('Krzysiek', 'Seriale', true, 0, 1, 3, 0);


-- DELETE
-- FROM question;
--
-- INSERT INTO question (id, image_path, answer, category)
-- VALUES ('q1', 'images/wedkarstwo/splawik.jpg', 'Spławik', 'Wędkarstwo'),
--        ('q2', 'images/wedkarstwo/wedka.jpg', 'Wędka', 'Wędkarstwo'),
--        ('q3', 'images/historia/napoleon.jpg', 'Napoleon Bonaparte', 'Historia'),
--        ('q4', 'images/historia/mieszko.jpg', 'Mieszko I', 'Historia'),
--        ('q5', 'images/muzyka/gitara.jpg', 'Gitara', 'Muzyka'),
--        ('q6', 'images/muzyka/skrzypce.jpg', 'Skrzypce', 'Muzyka');

