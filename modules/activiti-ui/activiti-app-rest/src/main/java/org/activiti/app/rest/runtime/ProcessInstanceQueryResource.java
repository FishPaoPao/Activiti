/* Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.activiti.app.rest.runtime;

import com.fasterxml.jackson.databind.JsonNode;
import org.activiti.app.model.common.ResultListDataRepresentation;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
public class ProcessInstanceQueryResource extends AbstractProcessInstanceQueryResource {

    @Autowired
    private TaskService taskService;

	@Override
    @RequestMapping(value = "/rest/query/process-instances", method = RequestMethod.POST, produces = "application/json")
    public ResultListDataRepresentation getProcessInstances(@RequestBody ObjectNode requestNode) {
	  return super.getProcessInstances(requestNode);
  }

    @RequestMapping(value = "/rest/query/process-instance", method = RequestMethod.POST, produces = "application/json")
    public String processInstanceQueryByTaskId(@RequestBody ObjectNode requestNode){
        JsonNode taskIdNode = requestNode.get("taskId");
        Task task = null;
        if (taskIdNode != null && !taskIdNode.isNull()){
            task = taskService.createTaskQuery().taskId(taskIdNode.asText()).singleResult();
        }
        assert task != null;
        String processInstanceId = task.getProcessInstanceId();
        return processInstanceId;
    }

}