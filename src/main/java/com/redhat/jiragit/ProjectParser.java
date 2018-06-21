/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements. See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.redhat.jiragit;

import java.io.File;

/**
 * @author Clebert Suconic
 */

public class ProjectParser {

   public static void main(String arg[]) {
      try {
         if (arg.length != 6) {
            printSyntax();
            System.exit(-1);
         }
         switch (arg[0]) {
            case "artemis":
               artemisProcess(arg[1], arg[2], arg[3], arg[4], Boolean.parseBoolean(arg[5]));
               break;
            case "amq":
               amqProcess(arg[1], arg[2], arg[3], arg[4], Boolean.parseBoolean(arg[5]));
               break;
            case "wildfly":
               wildflyProcess(arg[1], arg[2], arg[3], arg[4], Boolean.parseBoolean(arg[5]));
               break;
            case "qpid-jms":
               qpidJMSProcess(arg[1], arg[2], arg[3], arg[4], Boolean.parseBoolean(arg[5]));
               break;
            default:
               System.err.println("Invalid Argument: " + arg[0]);
               printSyntax();
               System.exit(-1);
         }

      } catch (Exception e) {
         e.printStackTrace();
         System.exit(-1);
      }
   }

   private static void printSyntax() {
      System.err.println("use Parser <project> <repository> <reportOutput> <from> <to> <rest : true|false>");
      System.err.println("    valid projects: artemis, amq, wildfly, qpid-jms");
   }

   private static void wildflyProcess(String clone, String output, String tag1, String tag2, boolean rest) throws Exception {

      JiraParser jiraParser = new JiraParser("WildFly JIRAs");
      jiraParser.setJira("WFLY-").setJiraBrowseURI("https://issues.jboss.org/browse/").
         setSampleJQL("https://issues.jboss.org/issues/?jql=project%20%3D%20WildFly%20AND%20KEY%20IN");

      if (rest) {
         jiraParser.setRestLocation("https://issues.jboss.org/rest/api/2/issue/");
      }

      GitParser parser = new GitParser(new File(clone), "https://github.com/wildfly/wildfly/").
         setSourceSuffix(".java", ".md", ".c", ".sh", ".groovy", ".adoc");
      parser.addJIRA(jiraParser);

      parser.addInterestingfolder("test").addInterestingfolder("docs/");
      File file = new File(output);
      parser.parse(file,tag1, tag2);


   }

   private static void artemisProcess(String clone, String output, String tag1, String tag2, boolean rest) throws Exception {

      JiraParser jiraParser = new JiraParser("ARTEMIS JIRAs");
      jiraParser.setJira("ARTEMIS-").setJiraBrowseURI("https://issues.apache.org/jira/browse/").
         setSampleJQL("https://issues.apache.org/jira/issues/?jql=project%20%3D%20ARTEMIS%20AND%20key%20in%20");

      if (rest) {
         jiraParser.setRestLocation("https://issues.apache.org/jira/rest/api/2/issue/");
      }

      GitParser parser = new GitParser(new File(clone), "https://github.com/apache/activemq-artemis/").
         setSourceSuffix(".java", ".md", ".c", ".sh", ".groovy");
      parser.addJIRA(jiraParser);

      parser.addInterestingfolder("test").addInterestingfolder("docs/").addInterestingfolder("examples/");
      File file = new File(output);
      parser.parse(file,tag1, tag2);
   }

   private static void qpidJMSProcess(String clone, String output, String tag1, String tag2, boolean rest) throws Exception {

      JiraParser jiraParser = new JiraParser("QPID JIRAs");
      jiraParser.setJira("QPIDJMS-").setJiraBrowseURI("https://issues.apache.org/jira/browse/").
         setSampleJQL("https://issues.apache.org/jira/issues/?jql=project%20%3D%20QPIDJMS%20AND%20key%20in%20");

      if (rest) {
         jiraParser.setRestLocation("https://issues.apache.org/jira/rest/api/2/issue/");
      }

      GitParser parser = new GitParser(new File(clone), "https://github.com/apache/activemq-artemis/").
         setSourceSuffix(".java", ".md", ".c", ".sh", ".groovy");
      parser.addJIRA(jiraParser);

      parser.addInterestingfolder("test").addInterestingfolder("docs/").addInterestingfolder("examples/");
      File file = new File(output);
      parser.parse(file,tag1, tag2);
   }

   private static void amqProcess(String clone, String output, String tag1, String tag2, boolean rest) throws Exception {


      GitParser parser = new GitParser(new File(clone), "https://github.com/apache/activemq-artemis/").
         setSourceSuffix(".java", ".md", ".c", ".sh", ".groovy");

      JiraParser artemisJIRA = new JiraParser("ARTEMIS");
      artemisJIRA.setJira("ARTEMIS-").setJiraBrowseURI("https://issues.apache.org/jira/browse/").
         setSampleJQL("https://issues.apache.org/jira/issues/?jql=project%20%3D%20ARTEMIS%20AND%20key%20in%20");

      if (rest) {
         artemisJIRA.setRestLocation("https://issues.apache.org/jira/rest/api/2/issue/");
      }
      parser.addJIRA(artemisJIRA);

      JiraParser entmqbrJIRA = new JiraParser("ENTMQBR");
      entmqbrJIRA.setJira("ENTMQBR-").setJiraBrowseURI("https://issues.apache.org/jira/browse/").
         setSampleJQL("https://issues.jboss.org/issues/?jql=project%20%3D%20ENTMQBR%20AND%20KEY%20IN");

      if (rest) {
         entmqbrJIRA.setRestLocation("https://issues.jboss.org/rest/api/2/issue/");
      }

      parser.addJIRA(entmqbrJIRA);

      parser.addInterestingfolder("test").addInterestingfolder("docs/").addInterestingfolder("examples/");
      File file = new File(output);
      parser.parse(file,tag1, tag2);
   }
}
