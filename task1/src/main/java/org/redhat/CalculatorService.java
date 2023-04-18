package org.redhat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Stateless
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CalculatorService {

    @PersistenceContext(unitName = "task1")
    private EntityManager em;

    @GET
    @Path("test")
    public String test(){
        return "bong";
    }

    @POST
    @Path("calc")
    public Map<String, Integer> calculate(CargoEntity obj){
        char operation = obj.getOperation();
        int result;
        if(operation=='+'){
            result = obj.getNumber1()+obj.getNumber2();
        }
        else if(operation=='-'){
            result = obj.getNumber1()-obj.getNumber2();
        }
        else if(operation=='*'){
            result = obj.getNumber1()*obj.getNumber2();
        }
        else if(operation=='/'){
            if(obj.getNumber2()==0)
                throw new IllegalStateException("KYS");
            result = obj.getNumber1()/obj.getNumber2();
        }
        else{
            throw new IllegalStateException("Operation not supported");
        }
        em.persist(obj);
        Map<String, Integer> hm= new HashMap<String, Integer>();
        hm.put("Result", new Integer(result));
        return hm;
    }

    @GET
    @Path("calculations")
    public List<CargoEntity> getCalculations(){
        return em.createNamedQuery("getAll",CargoEntity.class).getResultList();
    }
}
