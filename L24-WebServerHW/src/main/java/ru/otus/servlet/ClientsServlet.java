package ru.otus.servlet;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import ru.otus.crm.dto.ClientDto;
import ru.otus.crm.service.DBServiceClient;
import ru.otus.services.TemplateProcessor;

@SuppressWarnings({"java:S1989"})
public class ClientsServlet extends HttpServlet {

    private static final String CLIENTS_PAGE_TEMPLATE = "clients.html";
    private static final String TEMPLATE_ATTR_CLIENTS = "clients";

    private static final String PARAM_CLIENT_NAME = "name";
    private static final String PARAM_CLIENT_ADDRESS = "address";
    private static final String PARAM_CLIENT_PHONE = "phones";

    private final transient DBServiceClient dbServiceClient;
    private final transient TemplateProcessor templateProcessor;

    public ClientsServlet(TemplateProcessor templateProcessor, DBServiceClient dbServiceClient) {
        this.templateProcessor = templateProcessor;
        this.dbServiceClient = dbServiceClient;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse response) throws IOException {
        Map<String, Object> paramsMap = new HashMap<>();
        var clients = dbServiceClient.findAll();
        paramsMap.put(TEMPLATE_ATTR_CLIENTS, clients);
        response.setContentType("text/html");
        response.getWriter().println(templateProcessor.getPage(CLIENTS_PAGE_TEMPLATE, paramsMap));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var clientSaveNewDto = extractClientDtoFromRequest(req);
        dbServiceClient.saveClient(clientSaveNewDto);
        resp.sendRedirect("/clients");
    }

    private ClientDto extractClientDtoFromRequest(HttpServletRequest request) {
        String name = request.getParameter(PARAM_CLIENT_NAME);
        String address = request.getParameter(PARAM_CLIENT_ADDRESS);
        String phone = request.getParameter(PARAM_CLIENT_PHONE);
        return new ClientDto(null, name, address, phone);
    }
}
