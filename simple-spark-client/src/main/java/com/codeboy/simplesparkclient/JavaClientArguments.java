package com.codeboy.simplesparkclient;

import java.util.ArrayList;
import java.util.List;

 

public class JavaClientArguments {
	/*
	 * "--jar",
	 * "/home/john/hadoop_soft/CDH5/spark-0.9.0-cdh5.0.0/spark-examples_2.10-0.9.0-cdh5.0.0.jar"
	 * , "--class", "org.apache.spark.examples.SparkPi", "--args",
	 * "yarn-standalone", "--num-workers", "1", "--master-memory", "1g",
	 * "--worker-memory", "512m", "--worker-cores",
	 */

	public String[] toArgumentStringArray() {
		List<String> results = new ArrayList<String>();
		results.add("--jar");
		results.add(this.getJar());
		if (this.getAddJars() != null && this.getAddJars().length > 0) {
			results.add("--addJars");
			
			results.add (toString(this.getAddJars()));
		}
		if (this.getFiles() != null && this.getFiles().length > 0) {
			results.add("--files");
			results.add (toString(this.getFiles()));
		}
		results.add("--class");
		results.add(this.getClazz());

		results.add("--args");
		results.add(toString(this.getArgs()));

		results.add("--num-workers");
		results.add(this.getNumOfWorkers() + "");
		results.add("--master-memory");
		results.add(this.getMasterMemory());
		results.add("--worker-memory");
		results.add(this.getWorkerMemory());
		results.add("--worker-cores");
		results.add(this.getWorkerCores() + "");

		return results.toArray(new String[results.size()]);
	}

	private String toString(String[] stringArr) {
		StringBuilder sb  = new StringBuilder();
		if(stringArr!=null){
			for(String str:stringArr){
				sb.append(str).append(",");
			}
		}
		return sb.toString();
	}

	public JavaClientArguments(String jar, String clazz, String[] args,
			String[] addJars, String[] files, int numOfWorkers,
			String masterMemory, String workerMemory, int workerCores) {
		super();
		this.jar = jar;
		this.clazz = clazz;
		this.args = args;
		this.addJars = addJars;
		this.files = files;
		this.numOfWorkers = numOfWorkers;
		this.masterMemory = masterMemory;
		this.workerMemory = workerMemory;
		this.workerCores = workerCores;
	}

	String jar = "";
	String clazz = "";
	String[] args = null;
	String[] addJars = null;
	String[] files = null;
	int numOfWorkers = 1;
	String masterMemory = "512m";
	String workerMemory = "512m";
	int workerCores = 1;

	public String getJar() {
		return jar;
	}

	public void setJar(String jar) {
		this.jar = jar;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

	public String[] getAddJars() {
		return addJars;
	}

	public void setAddJars(String[] addJars) {
		this.addJars = addJars;
	}

	public String[] getFiles() {
		return files;
	}

	public void setFiles(String[] files) {
		this.files = files;
	}

	public int getNumOfWorkers() {
		return numOfWorkers;
	}

	public void setNumOfWorkers(int numOfWorkers) {
		this.numOfWorkers = numOfWorkers;
	}

	public String getMasterMemory() {
		return masterMemory;
	}

	public void setMasterMemory(String masterMemory) {
		this.masterMemory = masterMemory;
	}

	public String getWorkerMemory() {
		return workerMemory;
	}

	public void setWorkerMemory(String workerMemory) {
		this.workerMemory = workerMemory;
	}

	public int getWorkerCores() {
		return workerCores;
	}

	public void setWorkerCores(int workerCores) {
		this.workerCores = workerCores;
	}

}
