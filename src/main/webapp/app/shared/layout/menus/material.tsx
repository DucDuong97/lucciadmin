import React from 'react';
import MenuItem from 'app/shared/layout/menus/menu-item';
import { DropdownItem } from 'reactstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { Translate, translate } from 'react-jhipster';
import { NavLink as Link } from 'react-router-dom';
import { NavDropdown } from './menu-components';

export const MaterialMenu = props => (
  <NavDropdown
    icon="th-list"
    name={translate('global.menu.material-menu.main')}
    id="entity-menu"
    style={{ maxHeight: '80vh', overflow: 'auto' }}
  >
    <MenuItem icon="asterisk" to="/material">
      <Translate contentKey="global.menu.material-menu.material" />
    </MenuItem>
    <MenuItem icon="asterisk" to="/get-material">
      <Translate contentKey="global.menu.material-menu.getMaterial" />
    </MenuItem>
    {/* jhipster-needle-add-entity-to-menu - JHipster will add material-menu to the menu here */}
  </NavDropdown>
);
