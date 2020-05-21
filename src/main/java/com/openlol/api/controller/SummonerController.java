package com.openlol.api.controller;


import com.openlol.api.config.DatabaseConfig;
import com.openlol.api.model.Region;
import com.openlol.api.model.RiotUser;
import com.rethinkdb.net.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.HashMap;

//Indiciamos que es un controlador rest
@Controller
public class SummonerController {

    private final SimpMessagingTemplate template;
    private final DatabaseConfig databaseConfig;

    @Autowired
    public SummonerController(SimpMessagingTemplate template, DatabaseConfig databaseConfig) {
        this.template = template;
        this.databaseConfig = databaseConfig;
    }


    @MessageMapping("/summoners/update")
    public void greeting(RiotUser riotUser, Principal principal) {
        System.out.println("CCUURRENT USER: " + principal.getName());
        System.out.println("SUMMONER: " + riotUser);
        riotUser.setCurrentRegion(Region.NA);

        // search on db
        Result<Object> foundOpenlolUser = this.databaseConfig.getR().
                table("riot_user").filter(row -> row.g("uuid").eq(riotUser.getRealPuuid()))
                .run(this.databaseConfig.getConnection());

        // if no results, add it
        if (foundOpenlolUser.bufferedCount() == 0) {
            this.databaseConfig.getR().
                    table("riot_user").insert(
                    this.databaseConfig.getR().array(
                            this.databaseConfig.getR().hashMap("uuid", riotUser.getRealPuuid())
                    )
            ).run(this.databaseConfig.getConnection());
        }
/*
        this.databaseConfig.getR().table("riot_user").filter(
                row -> row.g("uuid").eq(riotUser.getRealPuuid())
        )
                .update(
                        Map.of("real_id", riotUser.getRealId(),
                                "real_puuid", riotUser.getRealPuuid(),
                                "real_account_id", riotUser.getRealAccountId(),
                                "current_region", riotUser.getCurrentRegion(),
                                "login_name", riotUser.getLoginName(),
                                "display_name", riotUser.getDisplayName())
                ).run(this.databaseConfig.getConnection());*/
        template.convertAndSendToUser(principal.getName(), "/summoners/update", riotUser);
        new HashMap<String, Object>() {
        };
    }
}
