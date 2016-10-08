package bao.distance;

import javax.xml.parsers.*;
import org.w3c.dom.*;

public class Test
{
	public static void main(String[] args)
	{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		try
		{
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document doc = db.parse("pet2.xml");

			NodeList dogList = doc.getElementsByTagName("dog");
			System.out.println("共有" + dogList.getLength() + "个dog节点");
			for (int i = 0; i < dogList.getLength(); i++)
			{
				Node dog = dogList.item(i);
				Element elem = (Element) dog;
				System.out.println("id:" + elem.getAttribute("id"));
				for (Node node = dog.getFirstChild(); node != null; node = node.getNextSibling())
				{
					if (node.getNodeType() == Node.ELEMENT_NODE)
					{
						String name = node.getNodeName();
						String value = node.getFirstChild().getNodeValue();
						System.out.print(name + ":" + value + "\t");
					}
				}
				System.out.println();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}