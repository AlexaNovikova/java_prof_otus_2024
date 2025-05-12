package ru.otus.controllers;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;
import ru.otus.dto.ClientDto;
import ru.otus.service.ClientService;

@Controller
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @GetMapping({"/", "/client/list"})
    public String clientsListView(Model model) {
        List<ClientDto> clients = clientService.findAll();
        model.addAttribute("clients", clients);
        return "clientsList";
    }

    @GetMapping("/client/create")
    public String clientCreateView(Model model) {
        model.addAttribute("client", new ClientDto());
        return "clientCreate";
    }

    @PostMapping("/client/save")
    public RedirectView clientSave(ClientDto client) {
        clientService.save(client);
        return new RedirectView("/", true);
    }

    @GetMapping("/client/delete")
    public String deleteClient(@RequestParam Long id) {
        clientService.deleteById(id);
        return "redirect:/";
    }
}
