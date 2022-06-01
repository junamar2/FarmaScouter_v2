package com.al415885.farmascouter_v2.mappings;

public class MTHResult {

    private String ui;
    private String name;
    private String definitions;
    private String atoms;

    public MTHResult(String ui, String name, String definitions, String atoms){
        this.ui = ui;
        this.name = name;
        this.definitions = definitions;
        this.atoms = atoms;
    }

    public String getName() {
        return this.name;
    }

    public String getDefinitions() {
        return this.definitions;
    }

    public String getAtoms() {
        return this.atoms;
    }
}
