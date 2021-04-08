package com.turkcell.poc.controller;


import com.turkcell.poc.model.ApiResponse;
import com.turkcell.poc.model.MenuCreateDto;
import com.turkcell.poc.model.MenuDto;
import com.turkcell.poc.model.MenuUpdateDto;
import com.turkcell.poc.service.MenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.util.List;
import javax.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/menu")
@Api(description = "Set of endpoints for Creating, Retrieving, Updating and Deleting of Menu.")
public class MenuController {

  @Autowired
  MenuService menuService;

  /**
   * Used to pull the menu list
   * @return
   * @throws EntityNotFoundException
   */
  @RequestMapping(method = RequestMethod.GET)
  @ApiOperation("Returns list of all Menu item in the system.")
  public ResponseEntity<ApiResponse<List<MenuDto>>> getMenuList() throws EntityNotFoundException {
    List<MenuDto> menuList = menuService.getMenuList();

    return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, menuList, "Success!"),
        HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation("This method is used to create new menu in the system.")
  public ResponseEntity<ApiResponse<MenuDto>> insertMenu(@RequestBody MenuCreateDto menuInputDto) {
    MenuDto menu = menuService.insertMenu(menuInputDto);

    return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, menu, "Success!"),
        HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
  @ApiOperation("This method is used to update new menu in the system.")
  public ResponseEntity<ApiResponse<MenuDto>> updateMenu(
      @RequestBody MenuUpdateDto menuUptdInputDto) {
    MenuDto menu = menuService.updateMenu(menuUptdInputDto);

    return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, menu, "Success!"),
        HttpStatus.OK);
  }

  @RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
  @ApiOperation("This method is used to delete new menu in the system.")
  public ResponseEntity<ApiResponse<Boolean>> deleteMenu(@PathVariable("id") String id) {
    Boolean result = menuService.menuDelete(id);

    return new ResponseEntity<>(new ApiResponse<>(HttpStatus.OK, result, "Success!"),
        HttpStatus.OK);
  }


}
