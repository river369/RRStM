package com.yi.block;

import com.yi.YiConstants;
import com.yi.utils.XmlReader;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Created by jianguog on 17/2/18.
 */
public class BlockInfoReader {

    public static void main(String[] args) {
        BlockInfoReader blockInfoReader = new BlockInfoReader();
        blockInfoReader.getBlockInfo(YiConstants.blockInfoFileString);
    }

    public void getBlockInfo(String blockInfoFile){
        XmlReader xmlReader = new XmlReader();
        Document blockDocument = xmlReader.readXmlFileToDocument(blockInfoFile);
        parseBlockInfo(blockDocument);
    }

    void parseBlockInfo(Document blockDocument){
        NodeList bkNodeList = blockDocument.getElementsByTagName("BK");
        BlockData blockData = new BlockData();
        for (int bkIndex = 0; bkIndex< bkNodeList.getLength(); bkIndex++){
            BK bk = new BK();
            blockData.getBkList().add(bk);
            Node bkNode = bkNodeList.item(bkIndex);
            Node bkNameNode = bkNode.getAttributes().getNamedItem("Name");
            bk.setName(bkNameNode.getNodeValue());
            //System.out.println(bk.getName());

            NodeList bcNodeList = bkNode.getChildNodes();
            //System.out.println(BCNodeList.getLength());
            for (int bcIndex = 0; bcIndex< bcNodeList.getLength(); bcIndex++){
                BC bc = new BC();
                bk.getBcList().add(bc);
                Node bcNode = bcNodeList.item(bcIndex);
                Node bcNameNode = bcNode.getAttributes().getNamedItem("Name");
                bc.setName(bcNameNode.getNodeValue());
                //System.out.println(bc.getName());
                Node stockNode = bcNode.getAttributes().getNamedItem("Stock");
                bc.setStocks(stockNode.getNodeValue().split(";"));
                //System.out.println(bc.getStocks().length);
            }
        }
        blockData.printBlockData();
    }

}