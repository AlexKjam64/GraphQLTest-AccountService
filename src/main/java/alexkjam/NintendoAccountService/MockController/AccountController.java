package alexkjam.NintendoAccountService.MockController;

import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import alexkjam.NintendoAccountService.Client.NintendoClient;
import alexkjam.NintendoAccountService.MockApp.Email;
import alexkjam.NintendoAccountService.MockApp.Family;
import alexkjam.NintendoAccountService.MockApp.Game;
import alexkjam.NintendoAccountService.MockApp.Name;
import alexkjam.NintendoAccountService.MockApp.Phone;
import alexkjam.NintendoAccountService.MockApp.Series;
import alexkjam.NintendoAccountService.MockApp.Username;

@Controller
public class AccountController{
    private final NintendoClient nintendoClient;

    public AccountController(NintendoClient nintendoClient){
        this.nintendoClient = nintendoClient;
    }


    @QueryMapping
    public Username username(@Argument("id") String id){
        System.out.println("Username");
        return nintendoClient.retrieve("http://localhost:8081/user/username/" + id, new ParameterizedTypeReference<Username>(){});
    }

    @SchemaMapping
    public List<Family> familyMembers(Username username){
        System.out.println("Family");
        return nintendoClient.retrieve("http://localhost:8081/user/username/familyIdLookUp/" + username.id(), new ParameterizedTypeReference<List<Family>>(){});
    }

    @SchemaMapping
    public Name name(Username username){
        System.out.println("Name");
        return nintendoClient.retrieve("http://localhost:8082/name/" + username.id(), new ParameterizedTypeReference<Name>(){});
    }

    @SchemaMapping
    public Phone phone(Username username){
        System.out.println("Phone");
        return nintendoClient.retrieve("http://localhost:8083/phone/" + username.id(), new ParameterizedTypeReference<Phone>(){});
    }

    @SchemaMapping
    public Email email(Username username){
        System.out.println("Email");
        return nintendoClient.retrieve("http://localhost:8084/email/" + username.id(), new ParameterizedTypeReference<Email>(){});
    }

    @SchemaMapping
    public List<Game> games(Username username){
        System.out.println("Games");
        return nintendoClient.retrieve("http://localhost:8085/videogames/games/user/" + username.id(), new ParameterizedTypeReference<List<Game>>(){});
    }

    @SchemaMapping
    public Series series(Game game){
        return nintendoClient.retrieve("http://localhost:8085/videogames/series/" + game.seriesId(), new ParameterizedTypeReference<Series>(){});
    }
}