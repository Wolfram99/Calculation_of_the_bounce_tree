package com.diplom.app.Service;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import com.diplom.app.Enum.Blocks;
import com.diplom.app.Model.NodeTree;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import static java.lang.Math.pow;

public class MappingXmlToCollections {

    public static <T, E> Optional<T> getKeyByValue(Map<T, E> map, E value) {
        return map.entrySet()
                .stream()
                .filter(entry -> Objects.equals(entry.getValue(), value))
                .map(Map.Entry::getKey)
                .findFirst();
    }

    public Map<String, NodeTree> getNodeMapCells(String xml) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        DocumentBuilder db = dbf.newDocumentBuilder();
//        Document doc = db.parse(this.getClass().getClassLoader().getResourceAsStream("temp.xml"));
        Document doc = db.parse(new InputSource(new StringReader(xml)));
        doc.getDocumentElement().normalize();

        NodeList list = doc.getElementsByTagName("*");
        Map<String, NodeTree> mapNodeCells = new HashMap<>();
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);

            if ("mxGeometry".equals(node.getNodeName()) || "mxPoint".equals(node.getNodeName())
                    || "Array".equals(node.getNodeName())) {
                continue;

            }

            switch (node.getNodeName()) {
                case "AND" -> {
                    NodeTree nodeTree = new NodeTree(Enum.valueOf(Blocks.class, node.getNodeName()));
                    String idTemp = node.getAttributes().getNamedItem("id").getNodeValue();
                    nodeTree.setT(Integer.parseInt(node.getAttributes().getNamedItem("T").getNodeValue()));
                    mapNodeCells.put(idTemp, nodeTree);
                    continue;
                }
                case "OR", "AMOUNT" -> {
                    NodeTree nodeTree = new NodeTree(Enum.valueOf(Blocks.class, node.getNodeName()));
                    String idTemp = node.getAttributes().getNamedItem("id").getNodeValue();
                    mapNodeCells.put(idTemp, nodeTree);
                    continue;
                }
                case "REF" -> {
                    NodeTree nodeTree = new NodeTree(Enum.valueOf(Blocks.class, node.getNodeName()));
                    String idTemp = node.getAttributes().getNamedItem("id").getNodeValue();
                    nodeTree.setData(Double.parseDouble(node.getAttributes().getNamedItem("lambda").getNodeValue()));
                    mapNodeCells.put(idTemp, nodeTree);
                    continue;
                }
            }

            if (!mapNodeCells.isEmpty()) {
                if (mapNodeCells.containsKey(node.getAttributes().getNamedItem("parent").getNodeValue())) {
                    NodeTree temp = mapNodeCells
                            .get(node.getAttributes().getNamedItem("parent").getNodeValue());
                    switch (node.getAttributes().getNamedItem("value").getNodeValue()) {
                        case "in1", "in":
                            temp.setIn1(Integer.parseInt(node.getAttributes().getNamedItem("id").getNodeValue()));
                            break;
                        case "in2":
                            temp.setIn2(Integer.parseInt(node.getAttributes().getNamedItem("id").getNodeValue()));
                            break;
                        case "out":
                            temp.setOut(Integer.parseInt(node.getAttributes().getNamedItem("id").getNodeValue()));
                            break;
                    }
                }
            }
        }
        return mapNodeCells;
    }

    private Map<Integer, Integer> getNodeMapEdges(String xml) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

        dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        DocumentBuilder db = dbf.newDocumentBuilder();
//        Document doc = db.parse(this.getClass().getClassLoader().getResourceAsStream("temp.xml"));
        Document doc = db.parse(new InputSource(new StringReader(xml)));

        doc.getDocumentElement().normalize();

        NodeList list = doc.getElementsByTagName("*");
        Map<Integer, Integer> mapEdges = new HashMap<>();
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);
            if (node.getAttributes().getNamedItem("target") != null) {
                mapEdges.put(Integer.valueOf(node.getAttributes().getNamedItem("source").getNodeValue()),
                        Integer.valueOf(node.getAttributes().getNamedItem("target").getNodeValue()));
            }
        }

        return mapEdges;
    }

    public NodeTree getGraph(String xml) throws ParserConfigurationException, SAXException, IOException {
        Map<String, NodeTree> mapNodeCells = this.getNodeMapCells(xml);
        Map<Integer, Integer> mapEdges = this.getNodeMapEdges(xml);
        NodeTree rootNodeTree = null;
        for (Map.Entry<String, NodeTree> nodeTreeCurrent : mapNodeCells.entrySet()) {
            if ((nodeTreeCurrent.getValue().getLeft() != null
                    && nodeTreeCurrent.getValue().getRight() != null)
                    || "AMOUNT".equals(nodeTreeCurrent.getValue().getName().toString())
                    || "REF".equals(nodeTreeCurrent.getValue().getName().toString())) {
                continue;
            }

            for (Map.Entry<String, NodeTree> nodeTreeTemp : mapNodeCells.entrySet()) {
                if ((nodeTreeCurrent.getValue().getLeft() != null
                        && nodeTreeCurrent.getValue().getRight() != null)) {
                    break;
                }
                if (nodeTreeCurrent == nodeTreeTemp || "AMOUNT".equals(nodeTreeTemp.getValue().getName().toString())) {
                    continue;
                } else {
                    switch (nodeTreeCurrent.getValue().getName()) {
                        case AND, OR -> {
                            if (mapEdges.containsValue(nodeTreeCurrent.getValue().getIn1())) {
                                int key = getKeyByValue(mapEdges, nodeTreeCurrent.getValue().getIn1()).get();
                                if (key == nodeTreeTemp.getValue().getOut()) {
                                    nodeTreeCurrent.getValue().setLeft(nodeTreeTemp.getValue());
                                    continue;
                                }
                            } else if (mapEdges.containsKey(nodeTreeCurrent.getValue().getIn1())) {
                                int value = mapEdges.get(nodeTreeCurrent.getValue().getIn1());
                                if (value == nodeTreeTemp.getValue().getOut()) {
                                    nodeTreeCurrent.getValue().setLeft(nodeTreeTemp.getValue());
                                    continue;
                                }
                            }
                            if (mapEdges.containsValue(nodeTreeCurrent.getValue().getIn2())) {
                                int key = getKeyByValue(mapEdges, nodeTreeCurrent.getValue().getIn2()).get();
                                if (key == nodeTreeTemp.getValue().getOut()) {
                                    nodeTreeCurrent.getValue().setRight(nodeTreeTemp.getValue());
                                }
                            } else if (mapEdges.containsKey(nodeTreeCurrent.getValue().getIn2())) {
                                int value = mapEdges.get(nodeTreeCurrent.getValue().getIn2());
                                if (value == nodeTreeTemp.getValue().getOut()) {
                                    nodeTreeCurrent.getValue().setRight(nodeTreeTemp.getValue());
                                }
                            }
                        }
                        case REF -> {
                            if (mapEdges.containsValue(nodeTreeCurrent.getValue().getOut())) {
                                int key = getKeyByValue(mapEdges, nodeTreeCurrent.getValue().getOut()).get();
                                if (key == nodeTreeTemp.getValue().getIn1()) {
                                    nodeTreeTemp.getValue().setLeft(nodeTreeCurrent.getValue());
                                } else if (key == nodeTreeTemp.getValue().getIn2()) {
                                    nodeTreeTemp.getValue().setRight(nodeTreeCurrent.getValue());
                                }
                            } else if (mapEdges.containsKey(nodeTreeCurrent.getValue().getOut())) {
                                int value = mapEdges.get(nodeTreeCurrent.getValue().getOut());
                                if (value == nodeTreeTemp.getValue().getIn1()) {
                                    nodeTreeTemp.getValue().setLeft(nodeTreeCurrent.getValue());
                                } else if (value == nodeTreeTemp.getValue().getIn2()) {
                                    nodeTreeTemp.getValue().setRight(nodeTreeCurrent.getValue());
                                }
                            }
                        }
                    }
                }

            }
        }
        int edge = 0;
        for (Map.Entry<String, NodeTree> entry : mapNodeCells.entrySet()) {
            if ("AMOUNT".equals(entry.getValue().getName().toString())) {
                int temp = entry.getValue().getIn1();
                for (Map.Entry<Integer, Integer> entryEdge : mapEdges.entrySet()) {
                    if (temp == entryEdge.getKey()) {
                        edge = entryEdge.getValue();
                    } else if (temp == entryEdge.getValue()) {
                        edge = entryEdge.getKey();
                    }
                    if (edge != 0) {
                        break;
                    }
                }
                if (edge != 0) {
                    break;
                }
            }
        }
        for (Map.Entry<String, NodeTree> entry : mapNodeCells.entrySet()) {
            if (entry.getValue().getOut() == edge) {
                rootNodeTree = entry.getValue();
                break;
            }
        }
        return rootNodeTree;

    }

}
