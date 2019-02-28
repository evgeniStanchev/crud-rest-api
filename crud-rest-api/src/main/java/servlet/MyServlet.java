package servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.PlayerService;

@WebServlet("/home")
public class MyServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @EJB
    private PlayerService service;

    @Override
    public void service(final HttpServletRequest req, final HttpServletResponse res) {
	try (final PrintWriter out = res.getWriter()) {
	    out.println("Hello world!");

//	    final String name = "Gerrard";
//	    final String position = "forward";
//	    final int age = 34;

	    service.createPlayerInDB("Arnold", 24, "right-wing");
//	    out.println("Created player");

//	    out.println("Footballers with name:" + name);
//	    out.println(service.getPlayersByName(name));
//
//	    out.println("Footballers with position:" + position);
//	    out.println(service.getPlayersByPosition(position));

//	    out.println("Footballers with age:" + age);
//	    out.println(service.getPlayersByAge(age));

	    out.println("Goodbye world!");
	} catch (final IOException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public void doPost(final HttpServletRequest req, final HttpServletResponse res) {
	try (final PrintWriter out = res.getWriter()) {

	} catch (final IOException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public void doGet(final HttpServletRequest req, final HttpServletResponse res) {
	try (final PrintWriter out = res.getWriter()) {

	} catch (final IOException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public void doPut(final HttpServletRequest req, final HttpServletResponse res) {
	try (final PrintWriter out = res.getWriter()) {

	} catch (final IOException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public void doDelete(final HttpServletRequest req, final HttpServletResponse res) {
	try (final PrintWriter out = res.getWriter()) {

	} catch (final IOException e) {
	    throw new RuntimeException(e);
	}
    }

}
