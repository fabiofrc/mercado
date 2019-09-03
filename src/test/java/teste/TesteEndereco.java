///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package teste;
//
//import com.gdados.projeto.facade.EnderecoFacade;
//import com.gdados.projeto.model.Endereco;
//import com.vividsolutions.jts.geom.Coordinate;
//import com.vividsolutions.jts.geom.Geometry;
//import com.vividsolutions.jts.geom.GeometryFactory;
//import com.vividsolutions.jts.geom.Point;
//import java.util.Arrays;
//import java.util.List;
//
///**
// *
// * @author fabio
// */
//public class TesteEndereco {
//
//    public static void main(String[] args) {
//
//        EnderecoFacade ef = new EnderecoFacade();
//
////        List<Endereco> enderecos = ef.getAll();
////
////        for (Endereco e : enderecos) {
////            System.out.println("Latitude: " + e.getGeom().getCoordinate().x);
////            System.out.println("Longitude: " + e.getGeom().getCoordinate().y);
////        }
//        Endereco e = ef.getAllByCodigo(2L);
//
////        GeometryFactory geometryFactory = new GeometryFactory();
//
////        Geometry geometry = geometryFactory.createGeometry(e.getGeom());
////        geometry.setSRID(4326);
//
//        Point p = e.getGeom();
//        p.setSRID(4326);
//
//        System.out.println("Latitude: " + p.getCoordinate().x);
//        System.out.println("Longitude: " + p.getCoordinate().y);
////
////        System.out.println("Coordenadas" + Arrays.toString(geometry.getCoordinates()));
////
////        System.out.println("Distancia" + geometry.isWithinDistance(geometry, 100));
////        
////        Coordinate coordinate = new Coordinate();
////        Point p = geometryFactory.createPoint(coordinate);
////        
////        
////        double latitude = coordinate.x;
////        double longitude = coordinate.y;
//
////        Point p = 
//    }
//}
