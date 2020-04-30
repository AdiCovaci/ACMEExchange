package com.acme;

import model.Commodity;
import model.FiatCurrency;
import model.Figure;
import repositories.CurrencyRepository;
import repositories.InventoryRepository;
import service.ExchangeService;
import service.InventoryService;
import service.UIService;

import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.security.KeyException;

public class Main {

    public static void main(String[] args) {
        UIService ui = UIService.getInstance();
        try {
            ui.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
