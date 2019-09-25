package uk.ac.ebi.phenotype.stats.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class GenotypeEffectSize {
//    "Genotype effect size": {
//        "Value": 0.197017793252613,
//                "Variable": "Genotype",
//                "Model": "data_point ~ Genotype",
//                "Type": "Mean difference",
//                "Percentage change": {
//            "control Genotype": 0.238850438790275,
//                    "experimental Genotype": -2.65065613162032
//        }
//    },
    @JsonProperty("Value")
    private Double value;
    @JsonProperty("Variable")
    private String variable;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @JsonProperty("Type")
    private String type;

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @JsonProperty("Model")
    private String model;
    @JsonProperty("Percentage change")
    private Map<String, Double> percentageChange;

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getVariable() {
        return variable;
    }

    public void setVariable(String variable) {
        this.variable = variable;
    }

    public Map<String, Double> getPercentageChange() {
        return percentageChange;
    }

    public void setPercentageChange(Map<String, Double> percentageChange) {
        this.percentageChange = percentageChange;
    }
}
