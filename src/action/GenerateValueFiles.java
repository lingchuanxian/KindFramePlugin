package action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;

/**
 * class description here
 * author ling_cx
 * date 2017/10/24.
 */

public class GenerateValueFiles {
	private String dirStr = "";
	private int baseW;
	private int baseH;

	private final static String WTemplate = "<dimen name=\"x{0}\">{1}px</dimen>\n";
	private final static String HTemplate = "<dimen name=\"y{0}\">{1}px</dimen>\n";

	private final static String WTemplate2 = "<dimen name=\"x{0}\">{1}dp</dimen>\n";
	private final static String HTemplate2 = "<dimen name=\"y{0}\">{1}dp</dimen>\n";

	/**
	 * {0}-HEIGHT
	 */
	private final static String VALUE_TEMPLATE = "values-{0}x{1}";
	private final static String VALUE_TEMPLATE_DEFAULT = "values";

	private static final String SUPPORT_DIMESION = "320,480;480,800;480,854;540,960;600,1024;720,1184;720,1196;720,1280;768,1024;768,1280;800,1280;1080,1812;1080,1920;1440,2560;";

	private String supportStr = SUPPORT_DIMESION;

	public GenerateValueFiles(String dirStr,int baseX, int baseY, String supportStr) {
		this.baseW = baseX;
		this.baseH = baseY;
		this.dirStr = dirStr;

		if (!this.supportStr.contains(baseX + "," + baseY)) {
			this.supportStr += baseX + "," + baseY + ";";
		}

		this.supportStr += validateInput(supportStr);

		System.out.println(supportStr);

		File dir = new File(dirStr);
		if (!dir.exists()) {
			dir.mkdir();

		}
		System.out.println(dir.getAbsoluteFile());

	}

	/**
	 * @param supportStr
	 *            w,h_...w,h;
	 * @return
	 */
	private String validateInput(String supportStr) {
		StringBuffer sb = new StringBuffer();
		String[] vals = supportStr.split("_");
		int w = -1;
		int h = -1;
		String[] wh;
		for (String val : vals) {
			try {
				if (val == null || val.trim().length() == 0)
					continue;

				wh = val.split(",");
				w = Integer.parseInt(wh[0]);
				h = Integer.parseInt(wh[1]);
			} catch (Exception e) {
				System.out.println("skip invalidate params : w,h = " + val);
				continue;
			}
			sb.append(w + "," + h + ";");
		}

		return sb.toString();
	}

	public void generate() {
		String[] vals = supportStr.split(";");
		for (String val : vals) {
			String[] wh = val.split(",");
			generateXmlFile(Integer.parseInt(wh[0]), Integer.parseInt(wh[1]));
		}

	}

	public void generateDefault() {
		StringBuffer sbForWidth = new StringBuffer();
		sbForWidth.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		sbForWidth.append("<resources>");
		for (int i = 1; i <= 320; i++) {
			sbForWidth.append(WTemplate2.replace("{0}", i + "").replace("{1}",
					i + ""));
		}
		sbForWidth.append("</resources>");

		StringBuffer sbForHeight = new StringBuffer();
		sbForHeight.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		sbForHeight.append("<resources>");
		for (int i = 1; i <= 480; i++) {
			sbForHeight.append(HTemplate2.replace("{0}", i + "").replace("{1}",
					i + ""));
		}
		sbForHeight.append("</resources>");

		File fileDir = new File(dirStr + File.separator
				+ VALUE_TEMPLATE_DEFAULT);
		fileDir.mkdir();

		File layxFile = new File(fileDir.getAbsolutePath(), "lay_x.xml");
		File layyFile = new File(fileDir.getAbsolutePath(), "lay_y.xml");
		PrintOut(sbForWidth, sbForHeight, layxFile, layyFile);
	}

	private void PrintOut(StringBuffer sbForWidth, StringBuffer sbForHeight, File layxFile, File layyFile) {
		try {
			PrintWriter pw = new PrintWriter(new FileOutputStream(layxFile));
			pw.print(sbForWidth.toString());
			pw.close();
			pw = new PrintWriter(new FileOutputStream(layyFile));
			pw.print(sbForHeight.toString());
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	private void generateXmlFile(int w, int h) {

		StringBuffer sbForWidth = new StringBuffer();
		sbForWidth.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		sbForWidth.append("<resources>");
		float cellw = w * 1.0f / baseW;

		System.out.println("width : " + w + "," + baseW + "," + cellw);
		for (int i = 1; i < baseW; i++) {
			sbForWidth.append(WTemplate.replace("{0}", i + "").replace("{1}",
					change(cellw * i) + ""));
		}
		sbForWidth.append(WTemplate.replace("{0}", baseW + "").replace("{1}",
				w + ""));
		sbForWidth.append("</resources>");

		StringBuffer sbForHeight = new StringBuffer();
		sbForHeight.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
		sbForHeight.append("<resources>");
		float cellh = h *1.0f/ baseH;
		System.out.println("height : "+ h + "," + baseH + "," + cellh);
		for (int i = 1; i < baseH; i++) {
			sbForHeight.append(HTemplate.replace("{0}", i + "").replace("{1}",
					change(cellh * i) + ""));
		}
		sbForHeight.append(HTemplate.replace("{0}", baseH + "").replace("{1}",
				h + ""));
		sbForHeight.append("</resources>");

		File fileDir = new File(dirStr + File.separator
				+ VALUE_TEMPLATE.replace("{0}", h + "")//
				.replace("{1}", w + ""));
		fileDir.mkdir();

		File layxFile = new File(fileDir.getAbsolutePath(), "lay_x.xml");
		File layyFile = new File(fileDir.getAbsolutePath(), "lay_y.xml");
		PrintOut(sbForWidth, sbForHeight, layxFile, layyFile);
	}

	private static float change(float a) {
		int temp = (int) (a * 100);
		return temp / 100f;
	}

}