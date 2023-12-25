package at.htl.resource;

import at.htl.model.Car;
import at.htl.model.Costs;
import at.htl.model.Drive;
import at.htl.repository.CarRepository;
import at.htl.repository.CostsRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.List;

@Path("egd/costs/")
public class CostsResource {

    @Inject
    CostsRepository costsRepository;


    @POST
    @Path("")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCosts(final Costs costs) {
        if (costs == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        final Costs createdCar = costsRepository.addCosts(costs);
        return Response.created(URI.create("/api/costs/" + createdCar.getId())).build();
    }

    @DELETE
    @Path("{costsId}/")
    public Response removeCosts(@PathParam("costsId") Long costsId) {
        costsRepository.removeCosts(costsId);
        return Response.noContent().build();
    }
    //findCostsByUserId

    @GET
    @Path("costsByUserId/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCostsByUserId(@PathParam("userId") Long userId){
        List<Costs> costs = costsRepository.findCostsByUserId(userId);

        if (costs == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

         return Response.ok(costs).build();
    }

    @GET
    @Path("costsByCarId/{carId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCostsByCarId(@PathParam("carId") Long carId){
        List<Costs> costs = costsRepository.findAllCostsByCarId(carId);

        if (costs == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        return Response.ok(costs).build();
    }

}
