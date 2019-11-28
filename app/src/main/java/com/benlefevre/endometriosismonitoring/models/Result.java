package com.benlefevre.endometriosismonitoring.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Result {

    @SerializedName("nhits")
    @Expose
    private Integer nhits;
    @SerializedName("parameters")
    @Expose
    private Parameters parameters;
    @SerializedName("records")
    @Expose
    private List<Record> records = null;

    public Integer getNhits() {
        return nhits;
    }

    public void setNhits(Integer nhits) {
        this.nhits = nhits;
    }

    public Parameters getParameters() {
        return parameters;
    }

    public void setParameters(Parameters parameters) {
        this.parameters = parameters;
    }

    public List<Record> getRecords() {
        return records;
    }

    public void setRecords(List<Record> records) {
        this.records = records;
    }


    public class Fields {

        @SerializedName("nom")
        @Expose
        private String nom;
        @SerializedName("nom_reg")
        @Expose
        private String nomReg;
        @SerializedName("codes_ccam")
        @Expose
        private String codesCcam;
        @SerializedName("code_postal")
        @Expose
        private String codePostal;
        @SerializedName("nature_exercice")
        @Expose
        private String natureExercice;
        @SerializedName("nom_epci")
        @Expose
        private String nomEpci;
        @SerializedName("types_actes")
        @Expose
        private String typesActes;
        @SerializedName("sesam_vitale")
        @Expose
        private String sesamVitale;
        @SerializedName("civilite")
        @Expose
        private String civilite;
        @SerializedName("adresse")
        @Expose
        private String adresse;
        @SerializedName("nom_dep")
        @Expose
        private String nomDep;
        @SerializedName("libelle_profession")
        @Expose
        private String libelleProfession;
        @SerializedName("code_insee")
        @Expose
        private String codeInsee;
        @SerializedName("convention")
        @Expose
        private String convention;
        @SerializedName("nom_com")
        @Expose
        private String nomCom;
        @SerializedName("ccam_phase")
        @Expose
        private String ccamPhase;
        @SerializedName("code_profession")
        @Expose
        private Integer codeProfession;
        @SerializedName("coordonnees")
        @Expose
        private List<Double> coordonnees = null;
        @SerializedName("actes")
        @Expose
        private String actes;
        @SerializedName("telephone")
        @Expose
        private String telephone;

        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public String getNomReg() {
            return nomReg;
        }

        public void setNomReg(String nomReg) {
            this.nomReg = nomReg;
        }

        public String getCodesCcam() {
            return codesCcam;
        }

        public void setCodesCcam(String codesCcam) {
            this.codesCcam = codesCcam;
        }

        public String getCodePostal() {
            return codePostal;
        }

        public void setCodePostal(String codePostal) {
            this.codePostal = codePostal;
        }

        public String getNatureExercice() {
            return natureExercice;
        }

        public void setNatureExercice(String natureExercice) {
            this.natureExercice = natureExercice;
        }

        public String getNomEpci() {
            return nomEpci;
        }

        public void setNomEpci(String nomEpci) {
            this.nomEpci = nomEpci;
        }

        public String getTypesActes() {
            return typesActes;
        }

        public void setTypesActes(String typesActes) {
            this.typesActes = typesActes;
        }

        public String getSesamVitale() {
            return sesamVitale;
        }

        public void setSesamVitale(String sesamVitale) {
            this.sesamVitale = sesamVitale;
        }

        public String getCivilite() {
            return civilite;
        }

        public void setCivilite(String civilite) {
            this.civilite = civilite;
        }

        public String getAdresse() {
            return adresse;
        }

        public void setAdresse(String adresse) {
            this.adresse = adresse;
        }

        public String getNomDep() {
            return nomDep;
        }

        public void setNomDep(String nomDep) {
            this.nomDep = nomDep;
        }

        public String getLibelleProfession() {
            return libelleProfession;
        }

        public void setLibelleProfession(String libelleProfession) {
            this.libelleProfession = libelleProfession;
        }

        public String getCodeInsee() {
            return codeInsee;
        }

        public void setCodeInsee(String codeInsee) {
            this.codeInsee = codeInsee;
        }

        public String getConvention() {
            return convention;
        }

        public void setConvention(String convention) {
            this.convention = convention;
        }

        public String getNomCom() {
            return nomCom;
        }

        public void setNomCom(String nomCom) {
            this.nomCom = nomCom;
        }

        public String getCcamPhase() {
            return ccamPhase;
        }

        public void setCcamPhase(String ccamPhase) {
            this.ccamPhase = ccamPhase;
        }

        public Integer getCodeProfession() {
            return codeProfession;
        }

        public void setCodeProfession(Integer codeProfession) {
            this.codeProfession = codeProfession;
        }

        public List<Double> getCoordonnees() {
            return coordonnees;
        }

        public void setCoordonnees(List<Double> coordonnees) {
            this.coordonnees = coordonnees;
        }

        public String getActes() {
            return actes;
        }

        public void setActes(String actes) {
            this.actes = actes;
        }

        public String getTelephone() {
            return telephone;
        }

        public void setTelephone(String telephone) {
            this.telephone = telephone;
        }
    }

    public class Geometry {

        @SerializedName("type")
        @Expose
        private String type;
        @SerializedName("coordinates")
        @Expose
        private List<Double> coordinates = null;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public List<Double> getCoordinates() {
            return coordinates;
        }

        public void setCoordinates(List<Double> coordinates) {
            this.coordinates = coordinates;
        }
    }

    public class Parameters {

        @SerializedName("dataset")
        @Expose
        private String dataset;
        @SerializedName("timezone")
        @Expose
        private String timezone;
        @SerializedName("rows")
        @Expose
        private Integer rows;
        @SerializedName("format")
        @Expose
        private String format;
        @SerializedName("facet")
        @Expose
        private List<String> facet = null;

        public String getDataset() {
            return dataset;
        }

        public void setDataset(String dataset) {
            this.dataset = dataset;
        }

        public String getTimezone() {
            return timezone;
        }

        public void setTimezone(String timezone) {
            this.timezone = timezone;
        }

        public Integer getRows() {
            return rows;
        }

        public void setRows(Integer rows) {
            this.rows = rows;
        }

        public String getFormat() {
            return format;
        }

        public void setFormat(String format) {
            this.format = format;
        }

        public List<String> getFacet() {
            return facet;
        }

        public void setFacet(List<String> facet) {
            this.facet = facet;
        }

    }
    public class Record {

        @SerializedName("datasetid")
        @Expose
        private String datasetid;
        @SerializedName("recordid")
        @Expose
        private String recordid;
        @SerializedName("fields")
        @Expose
        private Fields fields;
        @SerializedName("geometry")
        @Expose
        private Geometry geometry;
        @SerializedName("record_timestamp")
        @Expose
        private String recordTimestamp;

        public String getDatasetid() {
            return datasetid;
        }

        public void setDatasetid(String datasetid) {
            this.datasetid = datasetid;
        }

        public String getRecordid() {
            return recordid;
        }

        public void setRecordid(String recordid) {
            this.recordid = recordid;
        }

        public Fields getFields() {
            return fields;
        }

        public void setFields(Fields fields) {
            this.fields = fields;
        }

        public Geometry getGeometry() {
            return geometry;
        }

        public void setGeometry(Geometry geometry) {
            this.geometry = geometry;
        }

        public String getRecordTimestamp() {
            return recordTimestamp;
        }

        public void setRecordTimestamp(String recordTimestamp) {
            this.recordTimestamp = recordTimestamp;
        }

    }
}
