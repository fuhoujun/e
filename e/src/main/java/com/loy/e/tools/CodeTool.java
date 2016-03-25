package com.loy.e.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Locale;

import com.loy.e.sys.domain.entity.TestEntity;
import com.loy.e.tools.model.EntityInfo;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */
public class CodeTool {
	public static class Options{
		boolean debug = false;
	}
	
	public static void main(String[] args) throws IOException, TemplateException {
		Options options = new Options();
		generateCode(TestEntity.class,options);
	}
	
	public static void generateCode(Class entityClass,Options options) throws IOException, TemplateException{
		String path = CodeTool.class.getResource("").getPath();
        String projectPath = path.replaceAll("classes(.)+", "");
        projectPath = path.replaceAll("target(.)+", "");
        
        String javaSrcPath = projectPath+"src/main/java/";
        String resourcePath = projectPath+"src/main/resources/";
        String i18nPath = resourcePath+"i18n/";
        
		String templatePath = CodeTool.class.getResource("").getPath()+"template";
		Configuration cfg = new Configuration();
		File file = new File(templatePath);
		cfg.setDirectoryForTemplateLoading(file);
		cfg.setEncoding(Locale.getDefault(), "UTF-8");
		EntityInfo entityInfo = new EntityInfo(TestEntity.class);
		File f = null;
		String entityName = null;
		String fileName = null;
		Template t = cfg.getTemplate("queryParam.ftl"); 
		String packageName = entityInfo.getDomainPackageName();
		if(!options.debug){
			packageName = packageName.replaceAll("\\.", "/");
			packageName = javaSrcPath+packageName;
			f = new File(packageName);
			if(!f.exists()){
				f.mkdirs();
			}
			entityName = entityInfo.getEntityName();
			entityName = entityName.replaceFirst("Entity", "");
			fileName = entityName+"QueryParam.java";
			f = new File(packageName,fileName);
			t.process(entityInfo, new OutputStreamWriter(new FileOutputStream(f))); 
		}
		
        t.process(entityInfo, new OutputStreamWriter(System.out)); 
       
        
        
        t = cfg.getTemplate("ql.ftl"); 
		packageName = entityInfo.getQlPackageName();
		if(!options.debug){
			packageName = packageName.replaceAll("\\.", "/");
			packageName = javaSrcPath+packageName;
			f = new File(packageName);
			if(!f.exists()){
				f.mkdirs();
			}
			entityName = entityInfo.getEntityName();
			entityName = entityName.replaceFirst("Entity", "");
			fileName = entityName+"-dynamic.xml";
			char[] temp = fileName.toCharArray();
			if(temp[0]>='A'&&temp[0]<='Z'){
			    temp[0]+=32;
			}
			fileName = new String(temp);
			f = new File(packageName,fileName);
			t.process(entityInfo, new OutputStreamWriter(new FileOutputStream(f))); 
		}
		
        t.process(entityInfo, new OutputStreamWriter(System.out)); 
        
        
        
        t = cfg.getTemplate("repository.ftl"); 
		packageName = entityInfo.getRepositoryPackageName();
		if(!options.debug){
			packageName = packageName.replaceAll("\\.", "/");
			packageName = javaSrcPath+packageName;
			f = new File(packageName);
			if(!f.exists()){
				f.mkdirs();
			}
			entityName = entityInfo.getEntityName();
			entityName = entityName.replaceFirst("Entity", "");
			fileName = entityName+"Repository.java";
			f = new File(packageName,fileName);
			 t.process(entityInfo, new OutputStreamWriter(new FileOutputStream(f))); 
		}
	    t.process(entityInfo, new OutputStreamWriter(System.out)); 
	    
        t = cfg.getTemplate("serviceImpl.ftl"); 
	    if(!options.debug){ 
			packageName = entityInfo.getServiceImplPackageName();
			packageName = packageName.replaceAll("\\.", "/");
			packageName = javaSrcPath+packageName;
			f = new File(packageName);
			if(!f.exists()){
				f.mkdirs();
			}
			entityName = entityInfo.getEntityName();
			entityName = entityName.replaceFirst("Entity", "");
			fileName = entityName+"ServiceImpl.java";
			f = new File(packageName,fileName);
			t.process(entityInfo, new OutputStreamWriter(new FileOutputStream(f))); 
		}
        t.process(entityInfo, new OutputStreamWriter(System.out)); 
        
        
        t = cfg.getTemplate("index.ftl");
        if(!options.debug){ 
			packageName = "webapp/static/"+entityInfo.getModelName();
			packageName = packageName.replaceAll("\\.", "/");
			packageName = javaSrcPath.replace("java", "")+packageName;
			f = new File(packageName);
			if(!f.exists()){
				f.mkdirs();
			}
			entityName = entityInfo.getEntityName();
			entityName = entityName.replaceFirst("Entity", "");
			fileName = entityName+"_index.html";
			char[] temp = fileName.toCharArray();
			if(temp[0]>='A'&&temp[0]<='Z'){
			    temp[0]+=32;
			}
			fileName = new String(temp);
			f = new File(packageName,fileName);
			t.process(entityInfo, new OutputStreamWriter(new FileOutputStream(f))); 
		}
        t.process(entityInfo, new OutputStreamWriter(System.out)); 

        
        t = cfg.getTemplate("i18n.ftl"); 
		packageName = entityInfo.getQlPackageName();
		if(!options.debug){
			packageName = entityInfo.getModelName();
			packageName = i18nPath+packageName;
			f = new File(packageName);
			if(!f.exists()){
				f.mkdirs();
			}
			fileName = entityInfo.getEntityNameFirstLower()+".properties";
			
			f = new File(packageName,fileName);
			
			t.process(entityInfo, new OutputStreamWriter(new FileOutputStream(f))); 
		}
		
        t.process(entityInfo, new OutputStreamWriter(System.out)); 
        
        
        System.out.println(entityInfo.getI18ns());
	}

}