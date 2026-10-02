package ru.laba.film.client.service;

import ru.laba.film.client.data.TcpFilmConnection;
import ru.laba.film.shared.MovieView;
import ru.laba.film.shared.WireProtocol;

import java.io.IOException;
import java.util.List;

/** Методы клиентского приложения, отделённые от консольного интерфейса. */
public final class FilmClientService implements AutoCloseable {
    private final TcpFilmConnection connection;
    public FilmClientService(TcpFilmConnection connection) { this.connection = connection; }

    public String authenticate(boolean register, String username, String password) throws IOException {
        String command = register ? "REGISTER" : "LOGIN";
        return requireSuccess(connection.call(command + "\t" + WireProtocol.encode(username)
                + "\t" + WireProtocol.encode(password))).message();
    }

    public Page list(String query, int offset, int limit) throws IOException {
        String command = query == null ? "LIST\t" + offset + "\t" + limit
                : "SEARCH\t" + offset + "\t" + limit + "\t" + WireProtocol.encode(query);
        TcpFilmConnection.Reply reply = requireSuccess(connection.call(command));
        return new Page(reply.movies(), reply.total());
    }

    public MovieView add(String title, int year) throws IOException {
        return onlyMovie(connection.call("ADD\t" + WireProtocol.encode(title) + "\t" + year));
    }

    public MovieView rate(long id, int rating) throws IOException {
        return onlyMovie(connection.call("RATE\t" + id + "\t" + rating));
    }

    public MovieView get(long id) throws IOException { return onlyMovie(connection.call("DETAIL\t" + id)); }

    private MovieView onlyMovie(TcpFilmConnection.Reply reply) throws IOException {
        requireSuccess(reply);
        if (reply.movies().isEmpty()) throw new IOException("Сервер не вернул сведения о фильме.");
        return reply.movies().get(0);
    }

    private TcpFilmConnection.Reply requireSuccess(TcpFilmConnection.Reply reply) throws IOException {
        if (!reply.success()) throw new IOException(reply.message());
        return reply;
    }

    @Override public void close() { connection.close(); }
    public record Page(List<MovieView> movies, int total) { }
}
