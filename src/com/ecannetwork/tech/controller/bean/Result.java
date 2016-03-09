package com.ecannetwork.tech.controller.bean;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.codehaus.jackson.map.annotate.JsonSerialize;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
@JsonSerialize(include = Inclusion.NON_NULL)
public class Result implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	
		private String message;
		private String moveup_dir_path;
		private String current_dir_path;
		private String current_url;
		private int total_count;
		private String url;
		private int error;
		private List<Hashtable<String, Object>> file_list = new ArrayList<Hashtable<String, Object>>();

		public String getMoveup_dir_path() {
			return moveup_dir_path;
		}

		public void setMoveup_dir_path(String moveup_dir_path) {
			this.moveup_dir_path = moveup_dir_path;
		}

		public String getCurrent_dir_path() {
			return current_dir_path;
		}

		public void setCurrent_dir_path(String current_dir_path) {
			this.current_dir_path = current_dir_path;
		}

		public String getCurrent_url() {
			return current_url;
		}

		public void setCurrent_url(String current_url) {
			this.current_url = current_url;
		}

		public int getTotal_count() {
			return total_count;
		}

		public void setTotal_count(int total_count) {
			this.total_count = total_count;
		}

		public List<Hashtable<String, Object>> getFile_list() {
			return file_list;
		}

		public void setFile_list(List<Hashtable<String, Object>> file_list) {
			this.file_list = file_list;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public int getError() {
			return error;
		}

		public void setError(int error) {
			this.error = error;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}
		
}
