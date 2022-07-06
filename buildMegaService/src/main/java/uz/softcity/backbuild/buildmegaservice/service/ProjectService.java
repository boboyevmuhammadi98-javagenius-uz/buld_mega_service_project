package uz.softcity.backbuild.buildmegaservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uz.softcity.backbuild.buildmegaservice.entity.Attachment;
import uz.softcity.backbuild.buildmegaservice.entity.AttachmentContent;
import uz.softcity.backbuild.buildmegaservice.entity.Project;
import uz.softcity.backbuild.buildmegaservice.payload.ApiResponse;
import uz.softcity.backbuild.buildmegaservice.payload.ProjectDto;
import uz.softcity.backbuild.buildmegaservice.repository.AttachmentContentRepository;
import uz.softcity.backbuild.buildmegaservice.repository.AttachmentRepository;
import uz.softcity.backbuild.buildmegaservice.repository.ProjectRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    @Autowired
    ProjectRepository projectRepository;
    @Autowired
    AttachmentRepository attachmentRepository;

    @Autowired
    AttachmentContentRepository attachmentContentRepository;

    /**
     * @return all object
     */
    public List<Project> getAllProject() {
        return projectRepository.findAllByIdNotNullOrderByIdAsc();
    }

    /**
     * @param id for found by id
     * @return found object
     */
    public Project getProjectById(long id) {
        return projectRepository.findById(id).orElse(null);
    }

    /**
     * @param projectDto - new object
     * @return ApiResponse class
     */
    public ApiResponse addProject(ProjectDto projectDto) {
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(projectDto.getAttachmentId());
        if (!optionalAttachment.isPresent())
            return new ApiResponse("attachment not found", false);
        projectRepository.save(
                new Project(
                        projectDto.getName(),
                        projectDto.getNameRu(),
                        projectDto.getDescription(),
                        projectDto.getDescriptionRu(),
                        optionalAttachment.get()
                )
        );
        return new ApiResponse("added", true);
    }

    /**
     * @param id         old object id for finding
     * @param projectDto new object
     * @return ApiResponse class
     */
    public ApiResponse editProject(long id, ProjectDto projectDto) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        if (!optionalProject.isPresent())
            return new ApiResponse("project not found", false);
        Optional<Attachment> optionalAttachment = attachmentRepository.findById(projectDto.getAttachmentId());
        if (!optionalAttachment.isPresent())
            return new ApiResponse("attachment not found", false);
        Project project = optionalProject.get();
        project.setName(projectDto.getName());
        project.setNameRu(projectDto.getNameRu());
        project.setDescription(projectDto.getDescription());
        project.setDescriptionRu(projectDto.getDescriptionRu());
        project.setAttachment(optionalAttachment.get());
        projectRepository.save(project);
        return new ApiResponse("edited", true);
    }

    /**
     * @param id object id for deleting
     * @return ApiResponse class
     */
    public ApiResponse deleteProject(long id) {
        Optional<Project> optionalProject = projectRepository.findById(id);
        if (!optionalProject.isPresent())
            return new ApiResponse("project not found", true);
        try {
            Optional<AttachmentContent> byAttachment_id = attachmentContentRepository.findByAttachment_Id(optionalProject.get().getAttachment().getId());
            attachmentContentRepository.delete(byAttachment_id.get());
            projectRepository.deleteById(id);
            attachmentRepository.delete(optionalProject.get().getAttachment());
            return new ApiResponse("deleted", true);
        } catch (Exception e) {
            return new ApiResponse("don't deleted", false);
        }
    }
}
