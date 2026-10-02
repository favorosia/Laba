package ru.laba.film.shared;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Locale;

/** Внутренний построчный протокол обмена клиента и сервера поверх TCP. */
public final class WireProtocol {
    private WireProtocol() { }
    public static String encode(String value) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(value.getBytes(StandardCharsets.UTF_8));
    }
    public static String decode(String value) {
        return new String(Base64.getUrlDecoder().decode(value), StandardCharsets.UTF_8);
    }
    public static void send(DataOutputStream output, String line) throws IOException {
        output.writeUTF(line);
        output.flush();
    }
    public static String receive(DataInputStream input) throws IOException { return input.readUTF(); }
    public static void respond(DataOutputStream output, String message, List<MovieView> movies, int total)
            throws IOException {
        send(output, "OK\t" + encode(message) + "\t" + movies.size() + "\t" + total);
        for (MovieView movie : movies) {
            send(output, movie.id() + "\t" + encode(movie.title()) + "\t" + movie.year() + "\t"
                    + String.format(Locale.ROOT, "%.2f", movie.averageRating()) + "\t"
                    + movie.ratingCount() + "\t" + movie.myRating());
        }
    }
    public static void fail(DataOutputStream output, String message) throws IOException {
        send(output, "ERR\t" + encode(message) + "\t0\t0");
    }
}
