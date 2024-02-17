package com.codecool.bookdb.manager;

import com.codecool.bookdb.view.UserInterface;

public abstract class Manager {
    protected UserInterface ui;

    public Manager(UserInterface ui) {
        this.ui = ui;
    }

    public void run() {
        boolean running = true;

        while (running) {
            ui.printTitle(getName());
            ui.printOption('l', "List");
            ui.printOption('a', "Add");
            ui.printOption('e', "Edit");
            ui.printOption('q', "Quit");

            // Enhanced switch - Java 13 feature
            switch (ui.choice("laeq")) {

                case 'l':
                    list();
                    break;
                case 'a':
                    add();
                    break;
                case 'e':
                    edit();
                    break;
                case 'q':
                    running = false;
                    break;
            }
        }
    }

    protected abstract String getName();
    protected abstract void list();
    protected abstract void add();
    protected abstract void edit();
}