spring-mybatis-maven-deploy-configuration
=========================================

Spring framework + MyBatis + Maven deploy configuration example





# Tomcat 설정

## 1. {톰캣경로}/conf/tomcat-users.xml 에서 "manager-script" 권한의 사용자를 정의

### tomcat-user.xml

tomcat-users.xml
    
    <?xml version='1.0' encoding='cp949'?>
    <!--
      Licensed to the Apache Software Foundation (ASF) under one or more
      contributor license agreements.  See the NOTICE file distributed with
      this work for additional information regarding copyright ownership.
      The ASF licenses this file to You under the Apache License, Version 2.0
      (the "License"); you may not use this file except in compliance with
      the License.  You may obtain a copy of the License at
    
          http://www.apache.org/licenses/LICENSE-2.0
    
      Unless required by applicable law or agreed to in writing, software
      distributed under the License is distributed on an "AS IS" BASIS,
      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
      See the License for the specific language governing permissions and
      limitations under the License.
    -->
    <tomcat-users>
    <user name="Administrator" password="pwd" roles="admin-gui,manager-gui,manager-script,manager-status,manager-jmx" />
    <!--
      NOTE:  By default, no user is included in the "manager-gui" role required
      to operate the "/manager/html" web application.  If you wish to use this app,
      you must define such a user - the username and password are arbitrary.
    -->
    <!--
      NOTE:  The sample user and role entries below are wrapped in a comment
      and thus are ignored when reading this file. Do not forget to remove
      <!.. ..> that surrounds them.
    -->
    <!--
      <role rolename="tomcat"/>
      <role rolename="role1"/>
      <user username="tomcat" password="tomcat" roles="tomcat"/>
      <user username="both" password="tomcat" roles="tomcat,role1"/>
      <user username="role1" password="tomcat" roles="role1"/>
    -->
      <role rolename="manager-script"/>
      <role rolename="manager-gui"/>
      <role rolename="manager-jmx"/>
      <role rolename="manager-status"/>
    </tomcat-users>
    
    

## 2. pom.xml 
     <!-- maven deploy -->
    <plugin>
        <groupId>org.apache.tomcat.maven</groupId>
        <artifactId>tomcat7-maven-plugin</artifactId>
        <version>2.1</version>
        <configuration>
            <!-- Path -->
            <path>/mavendeploytest</path>
            <!-- 구동중인 톰캣의 주소 + /manager/text -->
            <url>http://localhost:8081/manager/text</url>
            <!-- 톰캣 사용자 정보 -->
            <username>Administrator</username>
            <password>pwd</password>
        </configuration>
    </plugin>
    <!--  war plug in  -->
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-war-plugin</artifactId>
        <version>2.3</version>
        <configuration>
            <webXml>src\main\webapp\WEB-INF\web.xml</webXml>  
            <warSourceDirectory>${basedir}/src/main/webapp</warSourceDirectory>
            <webResources>
                <webResource>
                    <directory>${basedir}/src/main/resources-${environment}</directory>
                </webResource>
            </webResources>
        </configuration>
    </plugin>  
 
 
 
	<!-- profile definition -->
	<profiles>
		<profile>
			<id>local</id>
			<properties>
				<environment>local</environment>
			</properties>
		</profile>
		<profile>
			<id>dev</id>
			<properties>
				<environment>dev</environment>
			</properties>
		</profile>
		<profile>
			<id>stage</id>
			<properties>
				<environment>stage</environment>
			</properties>
		</profile>
	</profiles>
    
    

## 3. 배포(이클립스에서)
        1.Run as Configurations... - Maven Build...
        2.Browse Workspace로 배포 프로젝트 선택
        3.Goals : tomcat7:redeploy 
        4.Profile : stage        	
        5.Run        



# 기타 정보

배포될 설정파일의 구조는 src/main/resources-{env} 에 해당한다.
최종 배포(war)시 {tomcat}/webapps/{app}/WEB-INF/classes 에 배포환경에 따라 설정파일이 복사(overwrite)된다.


    

## FAQ

### Q. Pojo, Mojo ... 에러가 발생한다면? 

A. Check Skip Tests


### Q. Error assembling WAR: webxml attribute is required (or pre-existing WEB-INF/web.xml if executing in update mode)    


A.

    <plugin>            
      <groupId>org.apache.maven.plugins</groupId>
      <artifactId>maven-war-plugin</artifactId>
      <configuration>
        <webXml>src\main\webapp\WEB-INF\web.xml</webXml>        <= 요거 넣어줘!
      </configuration>
    </plugin>




### Q. Maven – Fatal error compiling: tools.jar not found

A. JRE로 설정되어 있어서 JDK로 변경해 줌!

Run eclipse - Windows -> Preferences - Java -> Installed JREs

Search ... set JDK path! (C:\Program Files\Java\jdk1.7.0_45\jre) & check jdk path 





    
    
    
    
