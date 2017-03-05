package com.yi.blocks;

import com.yi.YiConstants;
import com.yi.utils.XmlReader;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.Map;

/**
 * Created by jianguog on 17/2/18.
 */
public class BlockInfoReader {

    String blockInfoFile;

    public BlockInfoReader(String blockInfoFile) {
        this.blockInfoFile = blockInfoFile;
    }

    public BlockData getBlockData(){
        XmlReader xmlReader = new XmlReader();
        Document blockDocument = xmlReader.readXmlFileToDocument(blockInfoFile);
        return parseBlockData(blockDocument);
    }

    BlockData parseBlockData(Document blockDocument){
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
                bc.setStockIds(stockNode.getNodeValue().split(";"));
                //System.out.println(bc.getStocks().length);
            }
        }
        //blockData.printBlockData();
        return blockData;
    }

    public static void main(String[] args) {
        BlockInfoReader blockInfoReader = new BlockInfoReader(YiConstants.blockInfoFileString);
        blockInfoReader.getBlockData().printBlockData();
    }

}
