/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.loja.charts;
 
import br.com.loja.entities.Bairro;
import br.com.loja.jpa.controller.BairroJpaController;
import br.com.loja.jsf.controller.BairroController;
import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.faces.bean.ManagedBean;
import javax.persistence.Persistence;
import org.primefaces.model.chart.PieChartModel;
 
@ManagedBean
public class PieChart implements Serializable {
 
    private PieChartModel pieModel1;
    private PieChartModel pieModel2;
    private BairroJpaController jpaController = null;
    List<Bairro> bairros;
 
    @PostConstruct
    public void init() {
        createPieModels();
    }
 
    public PieChartModel getPieModel1() {
        return pieModel1;
    }
     
    public PieChartModel getPieModel2() {
        return pieModel2;
    }
     
    private void createPieModels() {
        createPieModel1();
        createPieModel2();
    }
    
    private BairroJpaController getJpaController() {
        if (jpaController == null) {
            jpaController = new BairroJpaController(Persistence.createEntityManagerFactory("lojaPU"));
        }
        return jpaController;
    }
 
    private void createPieModel1() {
        pieModel1 = new PieChartModel();
        
        //BairroController bc = new BairroController();
        bairros = getJpaController().findBairroEntities();
        pieModel1 = new PieChartModel();
        List <String> serNome = new ArrayList<String>();
        Map<String, Integer> map = new TreeMap<String,Integer>();
        for(Bairro b : bairros){
        serNome.add(b.getIdSer().getNome());
        }
        
        for(String nome : serNome){
            Integer count = map.get(nome);
            if(count == null){
                count = 0;
            }
            map.put(nome, count + 1);
        }
        
        for(String nome : map.keySet()){
            pieModel1.set(nome, map.get(nome));
        }
        /*pieModel1.set("Brand 1", 540);
         * pieModel1.set("Brand 2", 325);
         * pieModel1.set("Brand 3", 702);
         * pieModel1.set("Brand 4", 421);*/ 
        pieModel1.setTitle("Bairros x Regional");
        pieModel1.setShowDataLabels(true);
        pieModel1.setLegendPosition("w");
    }
     
    private void createPieModel2() {
        pieModel2 = new PieChartModel();
         
        pieModel2.set("Brand 1", 540);
        pieModel2.set("Brand 2", 325);
        pieModel2.set("Brand 3", 702);
        pieModel2.set("Brand 4", 421);
         
        pieModel2.setTitle("Custom Pie");
        pieModel2.setLegendPosition("e");
        pieModel2.setFill(false);
        pieModel2.setShowDataLabels(true);
        pieModel2.setDiameter(150);
    }
     
}