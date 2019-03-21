package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Reader;

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
    public void doPost(final HttpServletRequest req, final HttpServletResponse res) {
	try (final PrintWriter out = res.getWriter(); final Reader in = req.getReader()) {
	    out.println(service.addPlayers(in));
	} catch (final IOException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public void doGet(final HttpServletRequest req, final HttpServletResponse res) {
	try (final PrintWriter out = res.getWriter()) {
	    final String paramValue = req.getParameter("id");
	    if (paramValue == null) {
		out.println(service.getPlayers());
	    } else {
		out.println(service.getPlayer(paramValue));
	    }
	} catch (final IOException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public void doPut(final HttpServletRequest req, final HttpServletResponse res) {
	try (final PrintWriter out = res.getWriter(); final Reader in = req.getReader()) {
	    final String paramValue = req.getParameter("id");
	    if (paramValue == null) {
		out.println("Id is not specified!");
	    } else {
		out.println(service.updatePlayer(paramValue, in));
	    }
	} catch (final IOException e) {
	    throw new RuntimeException(e);
	}
    }

    @Override
    public void doDelete(final HttpServletRequest req, final HttpServletResponse res) {
	try (final PrintWriter out = res.getWriter()) {
	    final String paramValue = req.getParameter("id");
	    if (paramValue == null) {
		service.removePlayers();
		out.println("List is empty now!");
	    } else {
		out.println(service.removePlayer(paramValue));
	    }
	} catch (final IOException e) {
	    throw new RuntimeException(e);
	}
    }

}
