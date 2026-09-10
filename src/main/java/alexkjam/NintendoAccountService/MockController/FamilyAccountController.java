package alexkjam.NintendoAccountService.MockController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.graphql.data.method.annotation.BatchMapping;
import org.springframework.stereotype.Controller;

import alexkjam.NintendoAccountService.Client.NintendoClient;
import alexkjam.NintendoAccountService.MockApp.Email;
import alexkjam.NintendoAccountService.MockApp.Family;
import alexkjam.NintendoAccountService.MockApp.Game;
import alexkjam.NintendoAccountService.MockApp.Name;
import alexkjam.NintendoAccountService.MockApp.Phone;

@Controller
public class FamilyAccountController {
    private final NintendoClient nintendoClient;

    public FamilyAccountController(NintendoClient nintendoClient){
        this.nintendoClient = nintendoClient;
    }
    
    @BatchMapping(field = "name")
    public Map<Family, Name> batchName(List<Family> families){
        System.out.println("Name Batch");
        var ids = families.stream().map(family -> family.id()).collect(Collectors.toList());
        var maps = nintendoClient.retrieveBatch("http://localhost:8082/name/batch", new ParameterizedTypeReference<Map<String, Name>>(){}, ids);
        return families.stream().filter(family -> maps.get(family.id()) != null)
                                .collect(Collectors.toMap(family -> family, family -> maps.get(family.id())));
    }

    @BatchMapping(field = "phone")
    public Map<Family, Phone> batchPhone(List<Family> families){
        System.out.println("Phone Batch");
        var ids = families.stream().map(family -> family.id()).collect(Collectors.toList());
        var maps = nintendoClient.retrieveBatch("http://localhost:8083/phone/batch", new ParameterizedTypeReference<Map<String, Phone>>(){}, ids);
        return families.stream().filter(family -> maps.get(family.id()) != null)
                                .collect(Collectors.toMap(family -> family, family -> maps.get(family.id())));
    }

    @BatchMapping(field = "email")
    public Map<Family, Email> batchEmail(List<Family> families){
        System.out.println("Email Batch");
        var ids = families.stream().map(family -> family.id()).collect(Collectors.toList());
        var maps = nintendoClient.retrieveBatch("http://localhost:8084/email/batch", new ParameterizedTypeReference<Map<String, Email>>(){}, ids);
        return families.stream().filter(family -> maps.get(family.id()) != null)
                                .collect(Collectors.toMap(family -> family, family -> maps.get(family.id())));
    }

    @BatchMapping(field = "games")
    public Map<Family, List<Game>> batchGames(List<Family> families){
        System.out.println("Game Batch");
        var ids = families.stream().map(family -> family.id()).collect(Collectors.toList());
        var maps = nintendoClient.retrieveBatch("http://localhost:8085/videogames/games/batch", new ParameterizedTypeReference<Map<String, List<Game>>>(){}, ids);
        System.out.println("MAP: " + maps);
        var finalMap =  families.stream().filter(family -> maps.get(family.id()) != null)
                                .collect(Collectors.toMap(family -> family, family -> maps.get(family.id())));

        System.out.println("NINTENDO: Final Results: " + finalMap);
        return finalMap;
    }
}
