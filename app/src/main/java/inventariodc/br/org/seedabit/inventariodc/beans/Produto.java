package inventariodc.br.org.seedabit.inventariodc.beans;


import java.io.Serializable;

public class Produto {
    private String responsavel;
    private String localização;
    private String tombamento;
    private String descrição;
    private String obs;
    private short status;

    public Produto() { }

    public Produto(String responsavel, String localização, String tombamento, String descrição, String obs, short status) {
        this.responsavel = responsavel;
        this.localização = localização;
        this.tombamento = tombamento;
        this.descrição = descrição;
        this.obs = obs;
        this.status = status; //1-utilizado; 2-parado; 3-quebrado
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public String getLocalização() {
        return localização;
    }

    public void setLocalização(String localização) {
        this.localização = localização;
    }

    public String getTombamento() {
        return tombamento;
    }

    public void setTombamento(String tombamento) {
        this.tombamento = tombamento;
    }

    public String getDescrição() {
        return descrição;
    }

    public void setDescrição(String descrição) {
        this.descrição = descrição;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public short getStatus() {
        return status;
    }

    public void setStatus(short status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Produto{" +
                "responsavel='" + responsavel + '\'' +
                ", localização='" + localização + '\'' +
                ", tombamento='" + tombamento + '\'' +
                ", descrição='" + descrição + '\'' +
                ", obs='" + obs + '\'' +
                ", status=" + status +
                '}';
    }
}
