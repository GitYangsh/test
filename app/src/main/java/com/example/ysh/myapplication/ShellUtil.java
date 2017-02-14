package com.example.ysh.myapplication;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.TimeoutException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ShellUtils
 * <ul>
 * <strong>Check root</strong>
 * <li>{@link ShellUtil#checkRootPermission()}</li>
 * </ul>
 * <ul>
 * <strong>Execte command</strong>
 * <li>{@link ShellUtil#execCommand(String, boolean)}</li>
 * <li>{@link ShellUtil#execCommand(String, boolean, boolean)}</li>
 * <li>{@link ShellUtil#execCommand(List, boolean)}</li>
 * <li>{@link ShellUtil#execCommand(List, boolean, boolean)}</li>
 * <li>{@link ShellUtil#execCommand(String[], boolean)}</li>
 * <li>{@link ShellUtil#execCommand(String[], boolean, boolean)}</li>
 * </ul>
 * 
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2013-5-16
 */
public class ShellUtil {

	public static final String COMMAND_SU = "su";
	public static final String COMMAND_SH = "sh";
	public static final String COMMAND_EXIT = "exit\n";
	public static final String COMMAND_LINE_END = "\n";

	private static final String TAG = ShellUtil.class.getSimpleName();

	private ShellUtil() {
		throw new AssertionError();
	}

	/**
	 * check whether has root permission
	 * 
	 * @return
	 */
	public static boolean checkRootPermission() {
		return execCommand("echo root", true, false).result == 0;
	}

	/**
	 * execute shell command, default return result msg
	 * 
	 * @param command
	 *            command
	 * @param isRoot
	 *            whether need to run with root
	 * @return
	 * @see ShellUtil#execCommand(String[], boolean, boolean)
	 */
	public static CommandResult execCommand(String command, boolean isRoot) {
		return execCommand(new String[] { command }, isRoot, true);
	}

	/**
	 * execute shell commands, default return result msg
	 * 
	 * @param commands
	 *            command list
	 * @param isRoot
	 *            whether need to run with root
	 * @return
	 * @see ShellUtil#execCommand(String[], boolean, boolean)
	 */
	public static CommandResult execCommand(List<String> commands, boolean isRoot) {
		return execCommand(commands == null ? null : commands.toArray(new String[] {}), isRoot,
				true);
	}

	/**
	 * execute shell commands, default return result msg
	 * 
	 * @param commands
	 *            command array
	 * @param isRoot
	 *            whether need to run with root
	 * @return
	 * @see ShellUtil#execCommand(String[], boolean, boolean)
	 */
	public static CommandResult execCommand(String[] commands, boolean isRoot) {
		return execCommand(commands, isRoot, true);
	}

	/**
	 * execute shell command
	 * 
	 * @param command
	 *            command
	 * @param isRoot
	 *            whether need to run with root
	 * @param isNeedResultMsg
	 *            whether need result msg
	 * @return
	 * @see ShellUtil#execCommand(String[], boolean, boolean)
	 */
	public static CommandResult execCommand(String command, boolean isRoot, boolean isNeedResultMsg) {
		return execCommand(new String[] { command }, isRoot, isNeedResultMsg);
	}

	/**
	 * execute shell commands
	 * 
	 * @param commands
	 *            command list
	 * @param isRoot
	 *            whether need to run with root
	 * @param isNeedResultMsg
	 *            whether need result msg
	 * @return
	 * @see ShellUtil#execCommand(String[], boolean, boolean)
	 */
	public static CommandResult execCommand(List<String> commands, boolean isRoot,
			boolean isNeedResultMsg) {
		return execCommand(commands == null ? null : commands.toArray(new String[] {}), isRoot,
				isNeedResultMsg);
	}

	/**
	 * execute shell commands
	 * 
	 * @param commands
	 *            command array
	 * @param isRoot
	 *            whether need to run with root
	 * @param isNeedResultMsg
	 *            whether need result msg
	 * @return <ul>
	 *         <li>if isNeedResultMsg is false, {@link CommandResult#successMsg}
	 *         is null and {@link CommandResult#errorMsg} is null.</li>
	 *         <li>if {@link CommandResult#result} is -1, there maybe some
	 *         excepiton.</li>
	 *         </ul>
	 */
	public static CommandResult execCommand(String[] commands, boolean isRoot,
			boolean isNeedResultMsg) {
		int result = -1;
		if (commands == null || commands.length == 0) {
			return new CommandResult(result, null, null);
		}

		Process process = null;
		BufferedReader successResult = null;
		BufferedReader errorResult = null;
		StringBuilder successMsg = null;
		StringBuilder errorMsg = null;

		DataOutputStream os = null;
		try {
			process = Runtime.getRuntime().exec(isRoot ? COMMAND_SU : COMMAND_SH);
			os = new DataOutputStream(process.getOutputStream());
			for (String command : commands) {
				if (command == null) {
					continue;
				}

				// donnot use os.writeBytes(commmand), avoid chinese charset
				// error
				os.write(command.getBytes());
				os.writeBytes(COMMAND_LINE_END);
				os.flush();
			}
			os.writeBytes(COMMAND_EXIT);
			os.flush();

			result = process.waitFor();
			// get command result
			if (isNeedResultMsg) {
				successMsg = new StringBuilder();
				errorMsg = new StringBuilder();
				successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
				errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
				String s;
				while ((s = successResult.readLine()) != null) {
					successMsg.append(s);
				}
				while ((s = errorResult.readLine()) != null) {
					errorMsg.append(s);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null) {
					os.close();
					os = null;
				}
				if (successResult != null) {
					successResult.close();
					successResult = null;
				}
				if (errorResult != null) {
					errorResult.close();
					errorResult = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (process != null) {
				process.destroy();
				process = null;
			}
		}
		return new CommandResult(result, successMsg == null ? null : successMsg.toString(),
				errorMsg == null ? null : errorMsg.toString());
	}

	/**
	 * This restarts only Android OS without rebooting the whole device. This
	 * does NOT work on all devices. This is done by killing the main init
	 * process named zygote. Zygote is restarted automatically by Android after
	 * killing it.
	 *
	 * @throws TimeoutException
	 */
	public static void restartAndroid() {
		killProcess("zygote");
	}

	/**
	 * This method can be used to kill a running process
	 *
	 * @param processName
	 *            name of process to kill
	 * @return <ul>
	 *         <li>if isNeedResultMsg is false, {@link CommandResult#successMsg}
	 *         is null and {@link CommandResult#errorMsg} is null.</li>
	 *         <li>if {@link CommandResult#result} is -1, there maybe some
	 *         excepiton.</li>
	 *         </ul>
	 */
	public static CommandResult killProcess(final String processName) {
		int result = -1;
		if (processName == null || processName.length() == 0) {
			return new CommandResult(result, null, "processName is empty");
		}

		Process process = null;
		BufferedReader successResult = null;
		BufferedReader errorResult = null;
		StringBuilder successMsg = null;
		StringBuilder errorMsg = null;

		DataOutputStream os = null;
		try {
			process = Runtime.getRuntime().exec(COMMAND_SH);
			os = new DataOutputStream(process.getOutputStream());

			// donnot use os.writeBytes(commmand), avoid chinese charset error
			String pid = getPidFromProcessName(processName);
			if (pid == null || pid.length() == 0) {
				return new CommandResult(result, null, "processPid is not found");
			}

			os.write(("kill -9 " + pid).getBytes());
			os.writeBytes(COMMAND_LINE_END);
			os.flush();

			os.writeBytes(COMMAND_EXIT);
			os.flush();

			result = process.waitFor();

			successMsg = new StringBuilder();
			errorMsg = new StringBuilder();
			successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
			errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));
			String s = null;
			while ((s = successResult.readLine()) != null) {
				successMsg.append(s);
			}

			while ((s = errorResult.readLine()) != null) {
				errorMsg.append(s);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				if (successResult != null) {
					successResult.close();
				}
				if (errorResult != null) {
					errorResult.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (process != null) {
				process.destroy();
			}
		}
		return new CommandResult(result, successMsg == null ? null : successMsg.toString(),
				errorMsg == null ? null : errorMsg.toString());
	}

	public static String getPidFromProcessName(String processName) {
		String pid = null;
		Process process = null;
		BufferedReader successResult = null;
		BufferedReader errorResult = null;

		DataOutputStream os = null;
		try {
			process = Runtime.getRuntime().exec(COMMAND_SH);
			os = new DataOutputStream(process.getOutputStream());

			// donnot use os.writeBytes(commmand), avoid chinese charset error
			os.write("ps".getBytes());
			os.writeBytes(COMMAND_LINE_END);
			os.flush();

			os.writeBytes(COMMAND_EXIT);
			os.flush();

			process.waitFor();

			successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
			errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));

			String line = null;
			String ps_regex = "^\\S+\\s+([0-9]+).*$";
			Pattern psPattern = Pattern.compile(ps_regex);
			while ((line = successResult.readLine()) != null) {
				if (line.contains(processName)) {
					Matcher psMatcher = psPattern.matcher(line);
					if (psMatcher.find()) {
						pid = psMatcher.group(1);
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null) {
					os.close();
				}
				if (successResult != null) {
					successResult.close();
				}
				if (errorResult != null) {
					errorResult.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (process != null) {
				process.destroy();
			}
		}
		return pid;
	}

	/**
	 * 获取app是否已安装
	 *
	 * @param pkgName 包名
	 * @return boolean
	 */
	public static boolean isInstalled(String pkgName) {
		String excCmd = "pm list packages |grep " + pkgName;
		Process process = null;
		BufferedReader successResult = null;
		BufferedReader errorResult = null;
		DataOutputStream os = null;
		boolean isInstalled = false;

		try {
			process = Runtime.getRuntime().exec(COMMAND_SH);
			os = new DataOutputStream(process.getOutputStream());

			// donnot use os.writeBytes(commmand), avoid chinese charset error
			os.write(excCmd.getBytes());
			os.writeBytes(COMMAND_LINE_END);
			os.flush();

			os.writeBytes(COMMAND_EXIT);
			os.flush();

			process.waitFor();

			successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));
			errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));

			String line = null;
			while ((line = successResult.readLine()) != null) {
				if (line.contains(pkgName)) {
					isInstalled = true;
				}
			}

			while ((line = errorResult.readLine()) != null) {
				Log.e(TAG, line);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null) {
					os.close();
					os = null;
				}
				if (successResult != null) {
					successResult.close();
					successResult = null;
				}
				if (errorResult != null) {
					errorResult.close();
					errorResult = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (process != null) {
				process.destroy();
				process = null;
			}
		}
		return isInstalled;
	}

	/**
	 * 唤起app
	 *
	 * @param component
	 *            : package/activity
	 * @return boolean
	 */
	public static boolean startActivity(String component) {
		String excCmd = "am start -n " + component;
		Process process = null;
		BufferedReader errorResult = null;
		DataOutputStream os = null;
		boolean isSuccess = true;

		try {
			process = Runtime.getRuntime().exec(COMMAND_SH);
			os = new DataOutputStream(process.getOutputStream());

			// donnot use os.writeBytes(commmand), avoid chinese charset error
			os.write(excCmd.getBytes());
			os.writeBytes(COMMAND_LINE_END);
			os.flush();

			os.writeBytes(COMMAND_EXIT);
			os.flush();

			process.waitFor();

			errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));

			String line = null;
			while ((line = errorResult.readLine()) != null) {
				Log.e(TAG, line);
				if(line.contains("Error") || line.contains("Permission Denial")) {
					isSuccess = false;
				}
			}	
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null) {
					os.close();
					os = null;
				}
				if (errorResult != null) {
					errorResult.close();
					errorResult = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (process != null) {
				process.destroy();
				process = null;
			}
		}
		return isSuccess;
	}

	/**
	 * 强制唤起app
	 * -S: force stop the target app before starting the activity
	 *
	 * @param component
	 *            : package/activity
	 * @return boolean
	 */
	public static boolean startActivityForce(String component) {
		String excCmd = "am start -S -n " + component;
		Process process = null;
		BufferedReader errorResult = null;
		DataOutputStream os = null;
		boolean isSuccess = true;

		try {
			process = Runtime.getRuntime().exec(COMMAND_SH);
			os = new DataOutputStream(process.getOutputStream());

			// donnot use os.writeBytes(commmand), avoid chinese charset error
			os.write(excCmd.getBytes());
			os.writeBytes(COMMAND_LINE_END);
			os.flush();

			os.writeBytes(COMMAND_EXIT);
			os.flush();

			process.waitFor();

			errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));

			String line = null;
			while ((line = errorResult.readLine()) != null) {
				Log.e(TAG, line);
				if(line.contains("Error") || line.contains("Permission Denial")) {
					isSuccess = false;
				}
			}	
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null) {
					os.close();
					os = null;
				}
				if (errorResult != null) {
					errorResult.close();
					errorResult = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (process != null) {
				process.destroy();
				process = null;
			}
		}
		return isSuccess;
	}
	
	/**
	 * 唤起app
	 *
	 * @param action
	 * @return boolean
	 */
	public static boolean startActivityByAction(String action) {
		String excCmd = "am start -a " + action;
		Process process = null;
		BufferedReader errorResult = null;
		DataOutputStream os = null;
		boolean isSuccess = true;

		try {
			process = Runtime.getRuntime().exec(COMMAND_SH);
			os = new DataOutputStream(process.getOutputStream());

			// donnot use os.writeBytes(commmand), avoid chinese charset error
			os.write(excCmd.getBytes());
			os.writeBytes(COMMAND_LINE_END);
			os.flush();

			os.writeBytes(COMMAND_EXIT);
			os.flush();

			process.waitFor();

			errorResult = new BufferedReader(new InputStreamReader(process.getErrorStream()));

			String line = null;
			while ((line = errorResult.readLine()) != null) {
				Log.e(TAG, line);
				if(line.contains("Error") || line.contains("Permission Denial")) {
					isSuccess = false;
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null) {
					os.close();
					os = null;
				}
				if (errorResult != null) {
					errorResult.close();
					errorResult = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (process != null) {
				process.destroy();
				process = null;
			}
		}
		return isSuccess;
	}

	/**
	 * 获取当前activity的包名
	 *
	 */
	public static String getCurrentPackageName() {
		String excCmd = "dumpsys window |grep mCurrentFocus";
		Process process = null;
		BufferedReader successResult = null;
		DataOutputStream os = null;
		String currentPkgName = "";

		try {
			process = Runtime.getRuntime().exec(COMMAND_SH);
			os = new DataOutputStream(process.getOutputStream());

			// donnot use os.writeBytes(commmand), avoid chinese charset error
			os.write(excCmd.getBytes());
			os.writeBytes(COMMAND_LINE_END);
			os.flush();

			os.writeBytes(COMMAND_EXIT);
			os.flush();

			process.waitFor();

			successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));

			StringBuilder successMsg = new StringBuilder();
			String line = null;
			while ((line = successResult.readLine()) != null) {
				successMsg.append(line);
			}

			line = successMsg.toString();
			String pkg_regex = "^\\s+mCurrentFocus=Window\\S+\\s+\\S+\\s+([a-zA-Z0-9.]+).*$";
			Pattern psPattern = Pattern.compile(pkg_regex);
			Matcher psMatcher = psPattern.matcher(line);
			if (psMatcher.find()) {
				currentPkgName = psMatcher.group(1);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null) {
					os.close();
					os = null;
				}
				if (successResult != null) {
					successResult.close();
					successResult = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (process != null) {
				process.destroy();
				process = null;
			}
		}
		return currentPkgName;
	}

	/**
	 * 获取当前activity的类名
	 *
	 */
	public static String getCurrentClassName() {
		String excCmd = "dumpsys window |grep mCurrentFocus";
		Process process = null;
		BufferedReader successResult = null;
		DataOutputStream os = null;
		String currentClassName = "";

		try {
			process = Runtime.getRuntime().exec(COMMAND_SH);
			os = new DataOutputStream(process.getOutputStream());

			// donnot use os.writeBytes(commmand), avoid chinese charset error
			os.write(excCmd.getBytes());
			os.writeBytes(COMMAND_LINE_END);
			os.flush();

			os.writeBytes(COMMAND_EXIT);
			os.flush();

			process.waitFor();

			successResult = new BufferedReader(new InputStreamReader(process.getInputStream()));

			StringBuilder successMsg = new StringBuilder();
			String line = null;
			while ((line = successResult.readLine()) != null) {
				successMsg.append(line);
			}

			line = successMsg.toString();
			String pkg_regex = "^\\s+mCurrentFocus=Window\\S+\\s+\\S+\\s+\\S+/([a-zA-Z0-9._]+).*$";
			Pattern psPattern = Pattern.compile(pkg_regex);
			Matcher psMatcher = psPattern.matcher(line);
			if (psMatcher.find()) {
				currentClassName = psMatcher.group(1);
				Log.d(TAG, "currentClassName:" + currentClassName);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			try {
				if (os != null) {
					os.close();
					os = null;
				}
				if (successResult != null) {
					successResult.close();
					successResult = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

			if (process != null) {
				process.destroy();
				process = null;
			}
		}
		return currentClassName;
	}
	/**
	 * result of command
	 * <ul>
	 * <li>{@link CommandResult#result} means result of command, 0 means normal,
	 * else means error, same to excute in linux shell</li>
	 * <li>{@link CommandResult#successMsg} means success message of command
	 * result</li>
	 * <li>{@link CommandResult#errorMsg} means error message of command result</li>
	 * </ul>
	 * 
	 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a>
	 *         2013-5-16
	 */
	public static class CommandResult {

		/** result of command **/
		public int result;
		/** success message of command result **/
		public String successMsg;
		/** error message of command result **/
		public String errorMsg;

		public CommandResult(int result) {
			this.result = result;
		}

		public CommandResult(int result, String successMsg, String errorMsg) {
			this.result = result;
			this.successMsg = successMsg;
			this.errorMsg = errorMsg;
		}
	}

}