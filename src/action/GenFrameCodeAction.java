package action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.Messages;

import java.io.File;

/**
 * class description here
 * author ling_cx
 * date 2017/10/19.
 */
public class GenFrameCodeAction extends AnAction {

	private Project mProject;
	private PluginUtils mPluginUtils;

	@Override
	public void actionPerformed(AnActionEvent event) {
		mProject = event.getData(PlatformDataKeys.PROJECT);
		init();
		mPluginUtils.refreshProject(event);
	}


	/**
	 * 初始化Dialog
	 */
	private void init(){
		CreateNewProjectDialog mDialog = new CreateNewProjectDialog(new CreateNewProjectDialog.DialogCallBack() {
			@Override
			public void ok(CreateNewProjectDialog dialog, String authorName) {
				mPluginUtils = new PluginUtils(mProject,authorName);
				createClassFiles();
				Messages.showInfoMessage(mProject,"KindAmdroidFrame Generate Success","Generate Result");
				dialog.setVisible(false);
			}
		});
		mDialog.setVisible(true);
	}

	/**
	 * 生成类文件
	 */
	private void createClassFiles() {
		//api
		mPluginUtils.createFile(PluginUtils.FileType.Java,"api/ApiEngine.txt","api");
		mPluginUtils.createFile(PluginUtils.FileType.Java,"api/ApiException.txt","api");
		mPluginUtils.createFile(PluginUtils.FileType.Java,"api/ApiService.txt","api");
		//base
		mPluginUtils.createFile(PluginUtils.FileType.Java,"base/BaseActivity.txt","base");
		mPluginUtils.createFile(PluginUtils.FileType.Java,"base/BaseFragment.txt","base");
		mPluginUtils.createFile(PluginUtils.FileType.Java,"base/BaseModel.txt","base");
		mPluginUtils.createFile(PluginUtils.FileType.Java,"base/BaseView.txt","base");
		mPluginUtils.createFile(PluginUtils.FileType.Java,"base/BasePresenter.txt","base");
		//bean
		mPluginUtils.createFile(PluginUtils.FileType.Java,"bean/HttpResult.txt","bean");
		mPluginUtils.createFile(PluginUtils.FileType.Java,"bean/MessageEvent.txt","bean");
		mPluginUtils.createFile(PluginUtils.FileType.Java,"bean/SubmitParams.txt","bean");
		//di
		mPluginUtils.createFile(PluginUtils.FileType.Java,"di/component/AppComponent.txt","di/component");
		mPluginUtils.createFile(PluginUtils.FileType.Java,"di/module/AppModule.txt","di/module");
		mPluginUtils.createFile(PluginUtils.FileType.Java,"di/scope/ActivityScope.txt","di/scope");
		mPluginUtils.createFile(PluginUtils.FileType.Java,"di/scope/FragmentScope.txt","di/scope");
		//global
		mPluginUtils.createFile(PluginUtils.FileType.Java,"global/Contants.txt","global");
		mPluginUtils.createFile(PluginUtils.FileType.Java,"global/AppContext.txt","global");
		mPluginUtils.createFile(PluginUtils.FileType.Java,"global/ActivityManager.txt","global");
		mPluginUtils.createFile(PluginUtils.FileType.Java,"global/RxFilter.txt","global");
		mPluginUtils.createFile(PluginUtils.FileType.Java,"global/RetryWithDelay.txt","global");
		//utils
		mPluginUtils.createFile(PluginUtils.FileType.Java,"utils/AESUtil.txt","utils");
		mPluginUtils.createFile(PluginUtils.FileType.Java,"utils/SPUtils.txt","utils");
		mPluginUtils.createFile(PluginUtils.FileType.Java,"utils/StringUtil.txt","utils");
		//ui
		mPluginUtils.createFile(PluginUtils.FileType.Java,"ui/activity/HomeActivity.txt","ui/activity");
		mPluginUtils.createFile(PluginUtils.FileType.Java,"ui/fragment/IndexFragment.txt","ui/fragment");

		//widget
		mPluginUtils.createFile(PluginUtils.FileType.Java,"widget/ToolBarSet.txt","widget");
		//gradle
		mPluginUtils.createFile("ProjectBuild.txt","project","build.gradle");
		mPluginUtils.createFile("config.txt","project","config.gradle");
		mPluginUtils.createFile("AppBuild.txt","app","build.gradle");
		//proguard-rules
		mPluginUtils.createFile("proguard-rules.txt","app","proguard-rules.pro");
		//res
		mPluginUtils.createFile(PluginUtils.FileType.Xml,"res/layout/base_layout.txt","res/layout");
		mPluginUtils.createFile(PluginUtils.FileType.Xml,"res/layout/activity_home.txt","res/layout");
		mPluginUtils.createFile(PluginUtils.FileType.Xml,"res/layout/fragment_index.txt","res/layout");
		mPluginUtils.createFile(PluginUtils.FileType.Xml,"res/values/colors.xml","res/values");
		mPluginUtils.createFile(PluginUtils.FileType.Xml,"res/values/styles.xml","res/values");
		mPluginUtils.createFile(PluginUtils.FileType.Xml,"res/values/strings.xml","res/values");


		//mPluginUtils.createFile(new File(this.getClass().getClassLoader().getResource("/template/res/values/colors.xml").getPath()), new File(mProject.getBasePath() + "/app/src/main/res/values/colors.xml"));
		//values && mipmap
		GenerateValue();
		try {
			mPluginUtils.copyFile(new File(this.getClass().getClassLoader().getResource("/template/res/mipmap/bg_splashs.jpg").getPath()), new File(mProject.getBasePath() + "/app/src/main/res/mipmap-xhdpi/bg_splashs.jpg"));
			mPluginUtils.copyFile(new File(this.getClass().getClassLoader().getResource("/template/res/mipmap/ic_empty.png").getPath()), new File(mProject.getBasePath() + "/app/src/main/res/mipmap-xhdpi/ic_empty.png"));
			mPluginUtils.copyFile(new File(this.getClass().getClassLoader().getResource("/template/res/mipmap/ic_error.png").getPath()), new File(mProject.getBasePath() + "/app/src/main/res/mipmap-xhdpi/ic_error.png"));
			mPluginUtils.copyFile(new File(this.getClass().getClassLoader().getResource("/template/res/mipmap/ic_net_error.png").getPath()), new File(mProject.getBasePath() + "/app/src/main/res/mipmap-xhdpi/ic_net_error.png"));
			mPluginUtils.copyFile(new File(this.getClass().getClassLoader().getResource("/template/res/mipmap/ic_not_data.png").getPath()), new File(mProject.getBasePath() + "/app/src/main/res/mipmap-xhdpi/ic_not_data.png"));
			/*mPluginUtils.copyFile(new File(this.getClass().getClassLoader().getResource("/template/res/values/colors.xml").getPath()), new File(mProject.getBasePath() + "/app/src/main/res/values/colors.xml"));
			mPluginUtils.copyFile(new File(this.getClass().getClassLoader().getResource("/template/res/values/styles.xml").getPath()), new File(mProject.getBasePath() + "/app/src/main/res/values/styles.xml"));
			mPluginUtils.copyFile(new File(this.getClass().getClassLoader().getResource("/template/res/values/strings.xml").getPath()), new File(mProject.getBasePath() + "/app/src/main/res/values/strings.xml"));*/
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	public void GenerateValue() {
		int baseW = 320;
		int baseH = 480;
		new GenerateValueFiles(mProject.getBasePath() + "/app/src/main/res",baseW, baseH, "").generate();
		new GenerateValueFiles(mProject.getBasePath() + "/app/src/main/res",baseW, baseH, "").generateDefault();
	}
}
